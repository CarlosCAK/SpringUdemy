package com.carlos.arqspring.arquiteturaspring.montadora;

import java.awt.*;

public class HonddaHRV extends Carro{


    public HonddaHRV(Motor motor) {
        super(motor);
        setModelo("HRV");
        setCor(Color.BLACK);
        setMontadora(Montadora.HONDA);

    }
}
