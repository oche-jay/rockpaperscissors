package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
@RequiredArgsConstructor
public class Beats {
    private Player player;
    private String verb = "beats";


    public String toString() {
        return  this.getVerb() + " " + this.getPlayer();
    }

}
