package userInterfaceLaag;

import javafx.scene.control.TableView;

import java.awt.*;

public class TableSetterGetter {

    Checkbox checkbox;

    public TableSetterGetter(Checkbox checkbox){
        this.checkbox = checkbox;
    }

    public Checkbox getCheckbox() {
        return checkbox;
    }

    public void setPresentie(Checkbox presentie) {
        this.checkbox = checkbox;
    }

}
