import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.twix.feature)
}

val localProperties =
    Properties().apply {
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }

android {
    namespace = "com.twix.settings"

    buildFeatures {
        buildConfig = true
    }

    val privacyPolicyUrl =
        localProperties.getProperty("privacy_policy_url")
            ?: error("privacy_policy_url is missing in local.properties")

    buildTypes {
        debug {
            buildConfigField("String", "PRIVACY_POLICY_URL", "\"$privacyPolicyUrl\"")
        }
        release {
            buildConfigField("String", "PRIVACY_POLICY_URL", "\"$privacyPolicyUrl\"")
        }
    }
}

dependencies {
    implementation(libs.browser)
}
