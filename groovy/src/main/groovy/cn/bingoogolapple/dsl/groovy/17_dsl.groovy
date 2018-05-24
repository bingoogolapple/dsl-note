package cn.bingoogolapple.dsl.groovy

// Domain-Specific Languages ==> http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html

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

// ====================================================

interface Node {
    String render()
}

class Content implements Node {
    String text

    Content(String text) {
        this.text = text
    }

    @Override
    String render() {
        return text
    }
}

class Tag implements Node {
    List<Node> children = []
    Map<String, String> properties = [:]
    String name
    Integer level = 0

    Tag(String name, Integer level) {
        this.name = name
        this.level = level
    }

    @Override
    String render() {
        StringBuilder result = new StringBuilder()
        level.times {
            result.append('    ')
        }

        result.append("<$name").append()
        if (!properties.isEmpty()) {
            properties.each { key, value ->
                result.append(" $key=\"$value\"")
            }
        }
        result.append('>')

        if (!children.isEmpty() && children.size() == 1 && children.get(0) instanceof Content) {
            result.append(children.get(0).render())
        } else if (!children.isEmpty()) {
            children.each {
                result.append('\n').append(it.render())
            }
            result.append('\n')

            level.times {
                result.append('    ')
            }
        }
        result.append("</$name>")
        return result.toString()
    }
}

class TagDsl {
    Tag tag

    TagDsl(Tag tag) {
        this.tag = tag
    }

    static Tag make(Closure closure) {
        TagDsl tagDsl = new TagDsl()
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = tagDsl
        closure.call()
        return tagDsl.tag
    }

    def methodMissing(String name, Object args) {
        if (args[0] instanceof Closure) {
            Tag param
            if (tag == null) {
                tag = new Tag(name, 0)
                param = tag
            } else {
                Tag child = new Tag(name, tag.level + 1)
                tag.children.add(child)
                param = child
            }
            Closure closure = args[0] as Closure
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure.delegate = new TagDsl(param)
            closure.call()
        } else if (args[0] instanceof String) {
            tag.properties.put(name, args[0])
        } else {
            return "the method ${name} is missing, the params is ${args}"
        }
    }

    @Override
    Object getProperty(String propertyName) {
        if (propertyName == 'tag') {
            return tag
        } else {
            Node content = new Content(propertyName)
            tag.children.add(content)
            return content
        }
    }
}

println TagDsl.make {
    html {
        head {
            meta {
                charset 'utf-8'
            }
            title {
                BGA
            }
        }
        body {
            style 'position: static; overflow: visible;'
            ul {
                id 'root'
                'class' 'test'
                li {
                    a {
                        href 'https://github.com/bingoogolapple'
                        GitHub
                    }
                }
                li {
                    a {
                        href 'http://www.bingoogolapple.cn'
                        个人博客
                    }
                }
            }
        }
    }
}.render()

// ====================================================

class DslSpec {
    int count = 3

    DslSpec plus(int value) {
        println "$count plus $value"
        count = count + value
        return this
    }

    DslSpec minus(int value) {
        println "$count minus $value"
        count = count - value
        return this
    }

    DslSpec multiply(int value) {
        count = count * value
        println "$count multiply $value"
        return this
    }

    DslSpec multiply(int value1, int value2) {
        count = count * value1 * value2
        println "$count multiply $value1 * $value2"
        return this
    }

    DslSpec div(int value) {
        count = count / value
        println "$count div $value"
        return this
    }

    DslSpec mod(int value) {
        count = count % value
        println "$count mod $value"
        return this
    }

    DslSpec square() {
        count = count * count
        println "$count square"
        return this
    }

    DslSpec given(Closure closure) {
        println "given"
        closure.call()
        return this
    }

    DslSpec when(Closure closure) {
        println "when"
        closure.call()
        return this
    }

    DslSpec then(Closure closure) {
        println "then"
        closure.call()
        return this
    }
}

static DslSpec make(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = DslSpec) Closure closure) {
    DslSpec dslSpec = new DslSpec()
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure.delegate = dslSpec
    closure.call()
    return dslSpec
}

DslSpec dslSpec = new DslSpec()

println make {
    plus(1).multiply(2)
    plus 1 multiply 2

    plus(dslSpec.count).multiply(2).div(dslSpec.count)
    plus dslSpec.count multiply 2 div dslSpec.count

    plus(1).multiply(2, 3).div(2)
    plus 1 multiply 2, 3 div 2

//    plus(value: 1).multiply(2)
//    plus value: 1 multiply 2

    given({}).when({}).then({})
    given {} when {} then {}

    plus(1).square().multiply(2)
    plus 1 square() multiply 2

    plus(1).count
    plus 1 count
}.count

