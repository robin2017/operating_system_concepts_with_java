package singleton;

/**
 * Created by robin on 2017/5/3.
 */
public class SingletonTest {
    public static void main(String[] args) {
        singleton_lazy_dcl_test();
    }


    public static void singleton_hunger_test() {
        Thread[] mts = new Thread[10];
        for (int i = 0; i < mts.length; i++) {
            mts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton_hunger.getInstance().hashCode());
                }
            });
        }
        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }

    public static void singleton_lazy_test() {
        Thread[] mts = new Thread[10];
        for (int i = 0; i < mts.length; i++) {
            mts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton_lazy.getInstance().hashCode());
                }
            });
        }
        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }


    public static void singleton_lazy_syn_method_test() {
        Thread[] mts = new Thread[10];
        for (int i = 0; i < mts.length; i++) {
            mts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton_lazy_syn_method.getInstance().hashCode());
                }
            });
        }
        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }

    public static void singleton_lazy_syn_section_test() {
        Thread[] mts = new Thread[10];
        for (int i = 0; i < mts.length; i++) {
            mts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton_lazy_syn_section.getInstance().hashCode());
                }
            });
        }
        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }

    public static void singleton_lazy_dcl_test() {
        Thread[] mts = new Thread[10];
        for (int i = 0; i < mts.length; i++) {
            mts[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Singleton_lazy_dcl.getInstance().hashCode());
                }
            });
        }
        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }
}

//1、饿汉式
class Singleton_hunger {
    private static Singleton_hunger instance = new Singleton_hunger();

    private Singleton_hunger() {
    }

    public static Singleton_hunger getInstance() {
        return instance;
    }
}

//2、懒汉式
class Singleton_lazy {
    private static Singleton_lazy instance = null;

    private Singleton_lazy() {
    }

    public static Singleton_lazy getInstance() {
        if (instance == null) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton_lazy();
        }
        return instance;
    }
}

//3、懒汉式,方法同步
class Singleton_lazy_syn_method {
    private static Singleton_lazy_syn_method instance = null;

    private Singleton_lazy_syn_method() {
    }

    public synchronized static Singleton_lazy_syn_method getInstance() {
        if (instance == null) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instance = new Singleton_lazy_syn_method();
        }
        return instance;
    }
}

//4、懒汉式,块同步
class Singleton_lazy_syn_section {
    private static Singleton_lazy_syn_section instance = null;

    private Singleton_lazy_syn_section() {
    }

    public static Singleton_lazy_syn_section getInstance() {
        try {
            synchronized (Singleton_lazy_syn_section.class) {
                if (instance == null) {
                    Thread.sleep(300);
                    instance = new Singleton_lazy_syn_section();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }
}

//5、懒汉式,DCL
class Singleton_lazy_dcl {
    volatile private static Singleton_lazy_dcl instance = null;
    private Singleton_lazy_dcl() {}
    public static Singleton_lazy_dcl getInstance() {
        if (instance == null) {
            try {
                synchronized (Singleton_lazy_dcl.class) {
                    if (instance == null) {
                        Thread.sleep(300);
                        instance = new Singleton_lazy_dcl();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}