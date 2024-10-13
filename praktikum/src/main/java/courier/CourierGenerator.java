package courier;

import java.time.LocalDateTime;

public class CourierGenerator {

    public Courier generic() {
        return new Courier("TeslaN", "p@ssword123", "Nikola");
    }

    public Courier random() {
        return new Courier("TeslaN"+ LocalDateTime.now(), "p@ssword123", "Nikola");
    }
}
