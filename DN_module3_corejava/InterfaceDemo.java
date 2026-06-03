public class InterfaceDemo {
    public static void main(String[] args) {
        Playable g = new Guitar();
        Playable p = new Piano();
        g.play();
        p.play();
    }
}
interface Playable {
    void play();
}

class Guitar implements Playable {
    public void play() {
        System.out.println("Playing guitar: Strum strum");
    }
}

class Piano implements Playable {
    public void play() {
        System.out.println("Playing piano: Plink plonk");
    }
}
/*
Playing guitar: Strum strum
Playing piano: Plink plonk
*/

