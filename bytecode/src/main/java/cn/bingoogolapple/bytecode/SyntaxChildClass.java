package cn.bingoogolapple.bytecode;

/*
Modifier    Class	Package	Subclass	World
public 	    Y   	Y	    Y	        Y
protected	Y	    Y	    Y   	    N
no modifier	Y	    Y	    N   	    N
private	    Y	    N	    N   	    N
 */
//「version=52」「access=33」「name=cn/bingoogolapple/bytecode/SyntaxChildClass」「signature=Lcn/bingoogolapple/bytecode/SyntaxSuperClass<Ljava/lang/String;>;」「superName=cn/bingoogolapple/bytecode/SyntaxSuperClass」「interfaces=[]」
public class SyntaxChildClass extends SyntaxSuperClass<String> {
    // 「access=1」「name=<init>」「desc=()V」「signature=null」「exceptions=null」

    //「access=1」「name=test11」「desc=()V」「signature=null」「exceptions=null」
    @Override
    public void test11() {
    }

    //「access=1」「name=test21」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    //public String test21(String p1) {

    //「access=4」「name=test21」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    @Override
    protected String test21(String p1) {
        return p1;
    }

    //「access=0」「name=test41」「desc=(Ljava/lang/String;)Ljava/lang/String;」「signature=null」「exceptions=null」
    @Override
    String test41(String p1) {
        return p1;
    }
}
