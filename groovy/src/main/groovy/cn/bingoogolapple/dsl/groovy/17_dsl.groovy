package cn.bingoogolapple.dsl.groovy

class BouquetConfiguration extends ArrayList<String> {

    int howMany(String flower) {
        return this.count { it == flower }
    }

    String toString() {
        return "Bouquet: " + flowers.toString()
    }
}

class FloristConfiguration {
    final Map<String, BouquetConfiguration> catalog = [:]

    String toString() {
        return "Florist: " + catalog.toString()
    }
}

class BouquetConfigurationDsl {
    private final BouquetConfiguration bouquet

    BouquetConfigurationDsl(BouquetConfiguration bouquet) {
        this.bouquet = bouquet
    }

    void flower(String flower) {
        bouquet << flower
    }
}

class FloristConfigurationDsl {
    private final FloristConfiguration florist

    FloristConfigurationDsl(FloristConfiguration florist) {
        this.florist = florist
    }

    BouquetConfiguration bouquet(
            String name,
            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = BouquetConfigurationDsl) Closure script
    ) {
        def bouquet = florist.catalog[name]
        if (bouquet == null) {
            bouquet = florist.catalog[name] = new BouquetConfiguration()
        }
        script.resolveStrategy = Closure.DELEGATE_FIRST
        script.delegate = new BouquetConfigurationDsl(bouquet)
        script()
        return bouquet
    }
}

class Florist {
    static FloristConfiguration create(
            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = FloristConfigurationDsl) Closure script
    ) {
        def florist = new FloristConfiguration()
        script.resolveStrategy = Closure.DELEGATE_FIRST
        script.delegate = new FloristConfigurationDsl(florist)
        script()
        return florist
    }
}

def florist = Florist.create {

    bouquet "meadow", {
        flower "cornflower"
        flower "cornflower"
        flower "poppy"
        flower "calendula"
    }

    bouquet "roses", {
        flower "rose"
        flower "rose"
        flower "rose"
        flower "rose"
    }

    // add another calendula to existing bouquet
    bouquet "meadow", {
        flower "calendula"
    }
}

println florist