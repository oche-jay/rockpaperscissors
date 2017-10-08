package model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
public class Rule {
    Player player;
    List<Beats> beats;

    public String toString() {
        String s = "";
        for (Beats b : beats){
           s += this.getPlayer() + " " + b + "\n";
       }
       return  s;
    }

}
