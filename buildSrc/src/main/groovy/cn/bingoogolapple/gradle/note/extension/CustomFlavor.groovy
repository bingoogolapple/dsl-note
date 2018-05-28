package cn.bingoogolapple.gradle.note.extension

class CustomFlavor {
    final String name
    String gender
    Integer age

    CustomFlavor(String name) {
        this.name = name
    }

    void gender(String gender) {
        this.gender = gender
    }

    void age(Integer age) {
        this.age = age
    }

    @Override
    String toString() {
        return "${this.class.simpleName} | name is $name | gender is $gender | age is $age"
    }
}
