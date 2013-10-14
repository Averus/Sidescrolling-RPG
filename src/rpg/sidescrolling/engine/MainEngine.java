package rpg.sidescrolling.engine;

import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import rpg.sidescrolling.configuration.RPGConfiguration;
import rpg.sidescrolling.configuration.RPGConfigurationService;
import rpg.sidescrolling.input.InputHandler;

import com.jogamp.opengl.util.FPSAnimator;

public class MainEngine extends GLCanvas implements GLEventListener {
	private final RPGConfigurationService rpgConfigurationService = RPGConfigurationService.getInstance();
	private InputHandler inputHandler;
	
	private final int height_px = rpgConfigurationService.getInt(RPGConfiguration.SCREEN_HEIGHT_PIXELS);
	private final int width_px = rpgConfigurationService.getInt(RPGConfiguration.SCREEN_HEIGHT_PIXELS);

	private GLU glu;
	
	public MainEngine() {
		addGLEventListener(this);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
		update();
		draw();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// dunno what to do here
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL_SMOOTH);
		
		//TODO Start the game here.
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL2 gl = drawable.getGL().getGL2();
		
		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION); // choose projection matrix
		gl.glLoadIdentity(); // reset projection matrix
		glu.gluOrtho2D(0, width_px, 0, height_px);

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}
	
	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}
	
	public void update() {
		// Update all sprite positions etc here
	}
	
	public void draw() {
		// Actually draw the stuff here
	}
	
	public static void main(String[] args) {
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Create the OpenGL rendering canvas
				GLCanvas canvas = new MainEngine();
				RPGConfigurationService configService = RPGConfigurationService
						.getInstance();
				int width = configService
						.getInt(RPGConfiguration.SCREEN_WIDTH);
				int height = configService
						.getInt(RPGConfiguration.SCREEN_HEIGHT);
				int fps = configService.getInt(RPGConfiguration.FPS);
				String version = configService
						.getString(RPGConfiguration.VERSION);

				canvas.setPreferredSize(new Dimension(width, height));

				// Create a animator that drives canvas' display() at the
				// specified FPS.
				final FPSAnimator animator = new FPSAnimator(canvas, fps, true);

				// Create the top-level container
				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's
													// Frame
				frame.getContentPane().add(canvas);
				((MainEngine) canvas).setInputHandler(new InputHandler(frame));
				
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Use a dedicated thread to run the stop() to ensure
						// that the
						// animator stops before program exits.
						new Thread() {
							@Override
							public void run() {
								if (animator.isStarted())
									animator.stop();
								System.exit(0);
							}
						}.start();
					}
				});
				frame.setTitle("Side Scrolling RPG: Version " + version);
				frame.pack();
				frame.setVisible(true);
				animator.start(); // start the animation loop
			}
		});
	}
}