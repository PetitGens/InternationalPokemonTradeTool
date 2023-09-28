module me.petitgens.internationalpokemontradetool {
    requires javafx.controls;
    requires javafx.fxml;
    requires tradingEngine;

    opens me.petitgens.internationalpokemontradetool to javafx.fxml;
    exports me.petitgens.internationalpokemontradetool;
}