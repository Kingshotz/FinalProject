# Strategy & Reasoning

## 3.1 Why Automate This?

**Locations (filter by city/type, search a street)** — This page is how people find a
branch or ATM near them. If the filter or search silently breaks after a deploy, the page
still looks fine but just shows wrong results, and nobody notices until a customer
complains. The logic lives entirely in how the dropdown, filter tabs, and search box talk
to each other client-side, so there's no API to unit test here — you have to drive the
actual browser to catch it.

**Mortgage calculator (valid inputs, invalid input)** — This one's not hypothetical: I
found a real bug where the Amount field's error message says "Income" instead of "Amount"
(entering 0 gives "Income must be greater than 1"). That's a content bug that only shows up
if you're actually reading the rendered page, which is exactly what UI automation is good
at and unit tests can't touch. Boundary values (min/max amount and term) also matter here
because a wrong monthly payment calculation directly affects whether someone applies for a
loan.

**App download banner (QR vs direct link)** — This is the required mobile-vs-desktop
scenario. Desktop shows a QR code, mobile shows a direct download button — same DOM, just
a CSS breakpoint deciding which one is interactive. That's a pure rendering question, so
automation is really the only way to check it reliably at both widths.

## 3.2 Selector Strategy

Here are the selectors I actually used, all checked against the live DOM instead of
guessed:

- `.tbcx-pw-atm-branches-section__list-item` — the site's own BEM-style class. Stable
  because it describes what the element *is*, not how it's styled.
- `input[placeholder='Income']` — this one's intentionally fragile. It's targeting the
  amount field, and the placeholder is literally the content bug I'm reporting, so if TBC
  fixes the label this selector breaks too — which is fine, that's the point.
- `.tbcx-pw-calculated-info__number` — same BEM pattern as above. I originally tried
  `byText("Monthly Contribution")` but that never matched because the label text is split
  by an icon element in the DOM.
- `byText("Period (years)").closest("div.label-with-input")` — anchored to the visible
  label since there's no unique class on the input itself. Breaks if the copy changes, but
  copy changes less often than CSS classes do.
- `.tbcx-pw-app-download-banner-section__qr-container` / `...__download-button` — same
  component confirmed on two different pages, so it's a shared component, not a one-off.

Priority order I followed: stable BEM class first, visible label text second (when no
class exists), structural navigation from a text anchor as a last resort, and generic
attributes like `type`/`placeholder` only when I'd confirmed live they were unique. One
thing I ruled out completely: auto-generated IDs like `tbcx-text-input-<uuid>` — I
reloaded the page three times and got three different UUIDs, so those are clearly
accessibility-linking artifacts, not something you can hook a selector to.

## 3.3 Flaky Test Awareness
I'll use LocationsTest.filterByCityAndAtmType since it had the most real flakiness of anything in the suite, and almost all of it showed up specifically on the mobile run.

The cookie consent banner was the main culprit. Looking at its CSS, on desktop it's a small fixed box in the bottom-right corner (max-width ~34.5rem), but under 600px width
it switches to a full-width bar pinned across the entire bottom of the screen (left/right: .9375rem). On a 390px viewport that banner is taking up a much bigger chunk of 
the visible page, so it's far more likely to sit on top of something the test needs to click — the city dropdown or filter tabs near the bottom of the initial view, for
example. On desktop there's enough space that it rarely overlaps anything, so the same test would often pass there and fail on mobile for a reason that had nothing to do
with the actual filter logic.

On top of that, my first version of the dismiss logic checked .exists() before waiting for the banner — an instant check, no polling. The banner takes a moment to mount 
after the page loads (Angular hydration), so on a slower mobile-width render the check would often run too early, find nothing yet, and skip dismissing it entirely. 
Then the banner would appear a beat later and block whatever the test tried to click next. Fixed it by waiting directly for the banner instead of checking existence first, 
so it actually polls until either it appears and gets dismissed, or the timeout passes and there was genuinely nothing to close.

The page also loads a pile of third-party scripts (GTM, Facebook Pixel, PostHog, VWO, Zendesk, a survey widget) that all load at unpredicta
ble speeds. Using pageLoadStrategy = "eager" means Selenide doesn't wait for all of that to finish, just for the DOM to be ready, and I bumped the timeout
from 10s to 15s once I saw the site's own widgets genuinely needed it.

Last thing: I saw occasional ERR_CONNECTION_RESET errors that had nothing to do with the test logic itself, so I added a retry (max 2 attempts) that only kicks in on failure — it won't save a test that's actually broken, since it'll still fail the same way on retry.

## 3.4 Mobile is not Desktop

Scenario: `AppDownloadBannerTest.bannerAdaptsDownloadMechanismToPlatform`.

This isn't just a layout difference — it's a different action being possible depending on
platform. On desktop the QR code container is visible and the download link exists in the
DOM but is computed 0×0, so it's not clickable at all. On mobile that flips: the QR hides
and the download link becomes the visible, tappable thing, pointing to a smart link
(`onelink.me`) that redirects to the right app store.

So the assertions are different per platform, not just checking "is something visible."
Desktop asserts the QR is shown and the button is specifically *not* visible. Mobile
asserts the button is visible and actually checks the `href` contains `onelink.me`, so it's
confirming the link goes somewhere real, not just that an element exists.

If this breaks, it breaks in two different unhelpful ways depending on direction. If QR
somehow showed on mobile, you'd be asking someone to scan a code with the phone they're
already holding — makes no sense. If the dead 0×0 button somehow showed on desktop instead
of the QR, there'd be no visible error at all, just a slightly worse app-install number
that nobody would think to investigate.
