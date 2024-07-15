
# BasicWallet4

## Overview

BasicWallet4 is a merchant-facing app designed to run on a Clover POS terminal. The app allows merchants to search for customers using their phone numbers on a WooCommerce site via API. If a successful result is found, the app retrieves the customer's name and store credit balance from WooCommerce. Merchants can adjust store credit, which is then pushed back to WooCommerce via API. Additionally, the app integrates with Clover's payment system to provide a new payment method option during checkout. Upon transaction completion, the new balance is adjusted via API to the WooCommerce customer account.

## Directory Structure

- **WalletViewModel.kt**: Contains the ViewModel for the wallet functionality.
- **build.gradle.kts**: The Gradle build script for the project.
- **gradlew**: The Gradle wrapper script for Unix-based systems.
- **gradlew.bat**: The Gradle wrapper script for Windows systems.
- **settings.gradle.kts**: The Gradle settings script.

### app

- **build.gradle.kts**: The Gradle build script for the app module.
- **clover**: Contains the release APK for the Clover POS terminal.
  - **release**: Contains the release APK and related metadata.
- **development**: Contains the development release APK.
  - **release**: Contains the development release APK and related metadata.
- **proguard-rules.pro**: ProGuard rules for code obfuscation.
- **release**: Contains the release APK and related metadata.
- **src**: Contains the source code for the app.
  - **androidTest**: Contains the Android instrumentation tests.
    - **java**: Contains the Java source code for tests.
      - **com.example.basicwallet**: Contains the test classes.
        - **ExampleInstrumentedTest.kt**: An example instrumentation test.
  - **main**: Contains the main source code for the app.
    - **AndroidManifest.xml**: The Android manifest file.
    - **java**: Contains the Java/Kotlin source code.
      - **com.example.basicwallet**: Contains the main app classes.
        - **MainActivity.kt**: The main activity of the app.
        - **MyApplication.kt**: The application class.
        - **connector**: Contains the connectors for interacting with external services.
          - **CustomCustomerConnector.kt**: Custom connector for customer interactions.
          - **MerchantConnector.kt**: Connector for merchant interactions.
          - **adapter**: Contains the adapters for connectors.
            - **CustomerAdapter.kt**: Adapter for customer data.
        - **model**: Contains the data models.
          - **Customer.kt**: Data model for customer.
          - **ErrorType.kt**: Enum for error types.
          - **Merchant.kt**: Data model for merchant.
        - **network**: Contains the network-related classes.
          - **CustomWorkerFactory.kt**: Custom worker factory for background tasks.
          - **CustomerSearchRepository.kt**: Repository for customer search.
          - **CustomerWorker.kt**: Worker for customer-related tasks.
          - **NetworkClient.kt**: Network client for API interactions.
          - **RetrofitInstance.kt**: Retrofit instance for API calls.
          - **WooCommerceApiClient.kt**: API client for WooCommerce.
        - **service**: Contains the service classes.
          - **CustomerSearchResponse.kt**: Response model for customer search.
          - **CustomerSearchService.kt**: Interface for customer search service.
          - **CustomerSearchServiceImpl.kt**: Implementation of customer search service.
          - **MerchantService.kt**: Service for merchant-related operations.
        - **ui**: Contains the UI-related classes.
          - **WalletScreen.kt**: UI for the wallet screen.
          - **theme**: Contains the theme-related classes.
            - **Color.kt**: Color definitions.
            - **Shape.kt**: Shape definitions.
            - **Theme.kt**: Theme definitions.
            - **Type.kt**: Typography definitions.
        - **viewmodel**: Contains the ViewModel classes.
          - **WalletViewModel.kt**: ViewModel for the wallet.
          - **WalletViewModelFactory.kt**: Factory for creating WalletViewModel instances.
    - **res**: Contains the resources for the app.
      - **drawable**: Contains the drawable resources.
        - **ic_launcher_background.xml**: Background for the app launcher icon.
      - **drawable-v24**: Contains the drawable resources for API level 24 and above.
        - **ic_launcher_foreground.xml**: Foreground for the app launcher icon.
      - **layout**: Contains the layout resources.
        - **activity_main.xml**: Layout for the main activity.
        - **fragment_wallet.xml**: Layout for the wallet fragment.
        - **item_customer.xml**: Layout for the customer item.
      - **mipmap-anydpi-v26**: Contains the mipmap resources for any DPI and API level 26 and above.
        - **ic_launcher.xml**: Launcher icon.
        - **ic_launcher_round.xml**: Round launcher icon.
      - **mipmap-hdpi**: Contains the mipmap resources for HDPI.
        - **ic_launcher.webp**: Launcher icon.
        - **ic_launcher_round.webp**: Round launcher icon.
      - **mipmap-mdpi**: Contains the mipmap resources for MDPI.
        - **ic_launcher.webp**: Launcher icon.
        - **ic_launcher_round.webp**: Round launcher icon.
      - **mipmap-xhdpi**: Contains the mipmap resources for XHDPI.
      - **mipmap-xxhdpi**: Contains the mipmap resources for XXHDPI.
      - **mipmap-xxxhdpi**: Contains the mipmap resources for XXXHDPI.
      - **values**: Contains the values resources.
      - **xml**: Contains the XML resources.

### gradle

- **wrapper**: Contains the Gradle wrapper files.

### gradle.properties

- Contains the Gradle properties for the project.

### my-release-key.jks

- The release key for signing the APK.

## Getting Started

To get started with the BasicWallet4 app, follow these steps:

1. **Clone the repository**:
   ```sh
   git clone https://github.com/diceybiz/BasicWallet4.git
   cd BasicWallet4
   ```

2. **Build the project**:
   ```sh
   ./gradlew build
   ```

3. **Run the app**:
   ```sh
   ./gradlew installDebug
   ```

## Contributing

If you would like to contribute to the project, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.
