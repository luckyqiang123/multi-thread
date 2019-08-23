import main.HelloWorld;
import main.HelloWorldImpl;
import main.JDKProxyExample;

public class Main {

    public static void main(String[] args) {

        JDKProxyExample jdk = new JDKProxyExample();
        HelloWorld proxy = (HelloWorld) jdk.bind(new HelloWorldImpl());
        proxy.sayHelloWorld();
    }
}
