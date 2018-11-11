package cn.bingoogolapple.bytecode.generated.asm;

import java.io.Serializable;

public class TestListDemo<T> implements Serializable {
    private static final long serialVersionUID = -5766514686676908255L;
    private String name;
    private T value;
    private String bga = "123";

    public TestListDemo() {
    }

    public TestListDemo(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public void setNameAndValue(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return name;
    }
}