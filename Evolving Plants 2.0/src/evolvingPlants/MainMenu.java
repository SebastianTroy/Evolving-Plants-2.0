package evolvingPlants;

import java.awt.Color;
import java.awt.Graphics2D;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TLabel;
import tComponents.components.TMenu;
import tComponents.components.TSlider;
import tComponents.utils.events.TActionEvent;
import tComponents.utils.events.TScrollEvent;
import evolvingPlants.simulation.Plant;
import evolvingPlants.simulation.Simulation;

public class MainMenu extends RenderableObject
	{
		private TMenu mainMenu;
		private TButton newSimButton = new TButton(0, 0, 0, 0, "New Simulation");
		private TButton resumeSimButton = new TButton(0, 0, 0, 0, "Resume Simulation");
		private TButton editorButton = new TButton(0, 0, 0, 0, "DNA Editor");

		private TMenu newSimMenu;
		private TSlider simWidthSlider = new TSlider(1200, 150, 500, TSlider.HORIZONTAL);
		private TButton startSimButton = new TButton(1450, 400, 300, 75, "Start Simulation");

		// New Simulation Variables
		private int simWidth = 800;

		@Override
		protected void initiate()
			{
				mainMenu = new TMenu(200, 0, 600, Main.canvasHeight, TMenu.VERTICAL);
				newSimMenu = new TMenu(1200, 0, 500, Main.canvasHeight, TMenu.VERTICAL);

				add(mainMenu);
				mainMenu.add(newSimButton);
				mainMenu.add(resumeSimButton);
				mainMenu.add(editorButton);

				add(newSimMenu);
				simWidthSlider.setRange(800, 4000);
				newSimMenu.add(new TLabel(0, 0, "Simulation Width in pixels"), false);
				newSimMenu.add(simWidthSlider);
				newSimMenu.add(startSimButton);
				newSimMenu.setBorderSize(22);
			}

		private final void reset()
			{
				mainMenu.setLocation(200, 0);
				newSimMenu.setLocation(1200, 0);
				simWidthSlider.setValue(800);
			}

		@Override
		public void tick(double secondsPassed)
			{}

		@Override
		protected void render(Graphics2D g)
			{
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, 1200, 600);
			}

		@Override
		public void tActionEvent(TActionEvent event)
			{
				Object source = event.getSource();
				
				if (source == newSimButton)
					{
						mainMenu.setX(0);
						newSimMenu.setX(640);
						simWidth = (int) simWidthSlider.getValue();					
					}
				else if (source == resumeSimButton)
					{
						if (Main.simWindow != null && Main.simWindow.sim != null && Main.simWindow.sim.simWidth >= 800)
							{
								Plant.lightMap = Main.simWindow.sim.lightMap;
								Plant.leafOpacitySlider = Main.simWindow.leafOpacitySlider;
								Plant.plantY = 550;
								
								changeRenderableObject(Main.simWindow);
								reset();
							}
					}
				else if (source == startSimButton)
					{
						Main.simWindow = new SimulationWindow();
						Main.simWindow.sim = new Simulation(simWidth);
						
						Plant.lightMap = Main.simWindow.sim.lightMap;
						Plant.leafOpacitySlider = Main.simWindow.leafOpacitySlider;
						Plant.plantY = 550;
						
						changeRenderableObject(Main.simWindow);
						reset();
					}
				else if (source == editorButton)
					{
						Plant.lightMap = Main.editor.lightMap;
						Plant.leafOpacitySlider = Main.editor.leafOpacitySlider;
						Plant.plantY = GeneEditor.plantY;
						
						changeRenderableObject(Main.editor);
						reset();
					}
			}

		@Override
		public void tScrollEvent(TScrollEvent event)
			{
				if (event.getSource() == simWidthSlider)
					{
						simWidth = (int) event.getScrollValue();
					}
			}
	}