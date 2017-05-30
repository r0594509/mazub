package jumpingalien.part2.internal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;

import jumpingalien.common.gui.AlienGUIUtils;
import jumpingalien.common.gui.AlienGameScreen;
import jumpingalien.common.gui.painters.AbstractAlienPainter;
import jumpingalien.common.sprites.ImageSprite;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;

public final class GameObjectPainter extends
		AbstractAlienPainter<AlienGameScreen<?, ?>> {

	private final AlienInfoProvider2<?> alienInfoProvider;
	private final ObjectInfoProvider objectInfoProvider;

	public GameObjectPainter(AlienGameScreen<?, ?> screen,
			AlienInfoProvider2<?> alienInfoProvider,
			ObjectInfoProvider objectInfoProvider) {
		super(screen);
		this.alienInfoProvider = alienInfoProvider;
		this.objectInfoProvider = objectInfoProvider;
	}

	@Override
	protected Part2Options getOptions() {
		return (Part2Options) super.getOptions();
	}

	@Override
	public void paintInWorld(java.awt.Graphics2D g) {
		paintMazubDebugInfo(g);
		paintPlants(g);
		paintSharks(g);
		paintSlimes(g);
	}

	protected void paintMazubDebugInfo(Graphics2D g) {
		alienInfoProvider.getAlienXY().ifPresent(
				xy -> paintDebugInfo(g, alienInfoProvider.getAlien(), xy));
	}

	protected void paintSharks(Graphics2D g) {
		for (Shark shark : objectInfoProvider.getSharks()) {
			objectInfoProvider.getLocation(shark).ifPresent(
					xy -> {
						objectInfoProvider.getCurrentSprite(shark).ifPresent(
								sprite -> paintSprite(g, sprite, xy));
						paintDebugInfo(g, shark, xy);
					});
		}
	}

	private final java.util.Map<School, Integer> schoolHueShifts = new HashMap<>();

	private int getHueShift(School school) {
		return schoolHueShifts.computeIfAbsent(school,
				s -> schoolHueShifts.size());
	}

	protected void paintSlimes(Graphics2D g) {
		for (Slime slime : objectInfoProvider.getSlimes()) {
			objectInfoProvider
					.getLocation(slime)
					.ifPresent(
							xy -> {
								objectInfoProvider
										.getCurrentSprite(slime)
										.ifPresent(
												sprite -> objectInfoProvider
														.getSchool(slime)
														.ifPresent(
																school -> paintSprite(
																		g,
																		sprite.shiftHue(getHueShift(school)),
																		xy)));
								paintDebugInfo(g, slime, xy);
							});
		}
	}

	private void paintDebugInfo(Graphics2D g, Object object, int[] xy) {
		if (getOptions().getDebugShowObjectString() && object != null) {
			// need to flip y
			g.scale(1, -1);
			g.setColor(Color.BLACK);
			g.setXORMode(Color.WHITE);
			g.drawString(object.toString(), xy[0], -xy[1]
					+ g.getFont().getSize());
			g.setPaintMode();
			g.scale(1, -1);
		}
	}

	protected void paintPlants(Graphics2D g) {
		for (Plant plant : objectInfoProvider.getPlants()) {
			objectInfoProvider.getLocation(plant).ifPresent(
					xy -> {
						objectInfoProvider.getCurrentSprite(plant).ifPresent(
								sprite -> paintSprite(g, sprite, xy));
						paintDebugInfo(g, plant, xy);
					});

		}
	}

	private void paintSprite(Graphics2D g, ImageSprite sprite, int[] xy) {
		if (((Part2Options) getOptions()).getDebugShowObjectLocationAndSize()) {
			paintLocationAndSize(g, xy,
					new int[] { sprite.getWidth(), sprite.getHeight() },
					getOptions().getDebugShowPixels());
		}
		AlienGUIUtils.drawImageInWorld(g, sprite.getImage(), xy[0], xy[1],
				false);
	}

	private static final Color SIZE_BORDER = Color.RED;
	private static final Color LOCATION_COLOR = Color.RED;
	private static final Color SIZE_FILL = new Color(0x880000ff, true);

	protected void paintLocationAndSize(Graphics2D g, int[] xy, int[] size,
			boolean smallStroke) {
		g.setColor(SIZE_FILL);
		g.fillRect(xy[0], xy[1], size[0], size[1]);

		g.setColor(SIZE_BORDER);

		Stroke oldStroke = g.getStroke();
		if (smallStroke) {
			// use a smaller stroke if individual pixels are painted
			g.setStroke(new BasicStroke(0.5f));
		}
		g.drawRect(xy[0], xy[1], size[0], size[1]);
		g.setStroke(oldStroke);

		g.setColor(LOCATION_COLOR);
		if (smallStroke) {
			// only fill 1 pixel if individual pixels are painted
			g.fillRect(xy[0], xy[1], 1, 1);
		} else {
			g.fillRect(xy[0], xy[1], 5, 5);
		}
	}
}
