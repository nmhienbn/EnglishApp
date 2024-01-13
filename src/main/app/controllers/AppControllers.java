package controllers;

public abstract class AppControllers {
    protected abstract void initialize();

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " has been collected!");
        super.finalize();
    }
}
