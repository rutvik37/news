# News Automation Project

This project is an automated test suite for a news web application using Playwright for Java. It simulates user interactions such as navigation, login, profile editing (including image upload), bookmarking news, viewing history, updating city, and checking notifications.

## Features

- **Navigation**: Automates navigation through Home, Video Gallery, Photo Gallery, and other sections.
- **Search**: Selects a random city and interacts with news items.
- **Login**: Completes login using a default city, mobile number, and OTP.
- **Profile Editing**: Edits the user profile and uploads a random image from the resources folder to test the change image functionality.
- **Bookmarks**: Views bookmarked news.
- **History**: Views news history.
- **Update City**: Changes the user's city.
- **Notifications**: Views notifications.
- **Logout**: Logs out of the application.
- **Headless Mode**: Runs tests in headless mode by default (can be configured).

## Profile Image Upload
- The `resources` folder contains three sample images.
- Each time the profile is edited, one of these images is randomly selected and uploaded to test the image change feature.

## How to Run
1. Ensure you have Java and Maven installed.
2. Place your test images in `src/resources/` if you want to use your own.
3. Run the project using Maven or your preferred IDE.

```
mvn test
```

## Adding New Functionality
- When you add new features or make changes, always update this `README.md` to document:
  - What the new feature does
  - How to use it
  - Any new configuration or files required

## Project Structure
- `src/main/java/com/rutvik/app/` — Main automation code
- `src/resources/` — Test images for profile upload
- `test/java/com/rutvik/app/` — Test classes
- `pom.xml` — Maven configuration

## Maintainers
- Please keep this file up to date with every significant code or feature change.
