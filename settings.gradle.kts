dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "Ecommerce-app"

include(":user")

include(":user")
include(":manager")
include(":common")
include(":features")

include(":navigation")
include(":navigation:user-navigation")
include(":navigation:manager-navigation")

include(":manager-features")
include(":manager-features:add-category")
include(":manager-features:login")
include(":manager-features:dashboard")
include(":manager-features:product")
include(":manager-features:edit-product")
include(":manager-features:categories")

include(":features")
include(":features:categories")
include(":features:search")
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


include(":ui-components:category-name")
include(":ui-components:product")
include(":ui-components:loading")
include(":ui-components")
include(":ui-components:image-card")
include(
    ":ui-components:user-locator",
    ":ui-components:category-image",
    ":ui-components:cart-item",
    ":ui-components:wish-item",
    ":features:wishlist",
    ":features:account",
)

include(
    ":data",
    ":data:local",
    ":data:remote",
    ":data:remote:firebase",
    ":data:remote:api",
    ":data:repository",
    ":data:model"
)