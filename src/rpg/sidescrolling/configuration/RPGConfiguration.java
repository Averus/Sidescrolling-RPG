package rpg.sidescrolling.configuration;

public enum RPGConfiguration {
	/**
	 * 
	 */
	VERSION("ONEONEONE"),
	
	/**
	 * Number of 'x' pixels to render on screen.
	 */
	SCREEN_WIDTH_PIXELS(640),
	
	/**
	 * Number of 'y' pixels to render on screen.
	 */
	SCREEN_HEIGHT_PIXELS(480),
	
	/**
	 * How wide the window should be.
	 */
	SCREEN_WIDTH(640),
	
	/**
	 * How high the window should be.
	 */
	SCREEN_HEIGHT(480),
	
	/**
	 * Desired frame rate (Frames Per Second)
	 */
	FPS(60);
	
	private Object value;
	
	private RPGConfiguration(final Object value) {
		this.value = value;
	}
	
	protected Object getValue() {
		return value;
	}
}
