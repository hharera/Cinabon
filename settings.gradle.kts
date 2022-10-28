dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "Cinabon"

include(
    ":user",
    ":common",
    ":common-test",
    ":features"
)

include(
    ":navigation",
    ":navigation:user-navigation",
    ":navigation:manager-navigation",
    ":navigation:user-login",
    ":navigation:home",
)

include(":manager-features")
include(":manager-features:add-category")
include(":manager-features:login")
include(":manager-features:dashboard")
include(":manager-features:product")
include(":manager-features:edit-product")
include(":manager-features:edit-categories")

include(
    ":features",
    ":features:wishlist",
    ":features:account",
)
include(
    ":features:categories",
    ":features:confirm-login",
)
include(
    ":features:search",
    ":features:home",
)
include(":features:market-location")
include(":features:shop")
include(":features:product")
include(":features:login")
include(":features:account-form")
include(":features:offer")
include(":features:cart")
include(":features:add-category")
include(":features:shop")
include(":features:product")
include(":features:splash")
include(":features:edit-product")
include(":features:dashboard")


include(
    ":ui-components",
    ":ui-components:user-locator",
)

include(
    ":data",
    ":data:local",
    ":data:remote",
    ":data:repository",
    ":data:datastore",
)