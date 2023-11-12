package views.comandline;

import java.util.ArrayList;

public abstract class TabBase {

    public abstract void init();

    public abstract ArrayList<String> getCommandList();

    public abstract void executeCommand(String command);

    public abstract String getTabName();

    public abstract void OnTabSelected();
}
