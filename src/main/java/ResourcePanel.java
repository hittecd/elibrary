

public class ResourcePanel {

    public interface ResourcePanelListener {};

    private ResourcePanelListener resourcePanelListener;

    public void setResourcePanelListener(ResourcePanelListener listener) {
        this.resourcePanelListener= listener;
    }

    private GameState currentState;

    public ResourcePanel(Game game) {
        game.registerUpdateStateListener(new Game.UpdateStateListener() {
            public void updateState(GameState newState) {

            }
        });
    }

}
