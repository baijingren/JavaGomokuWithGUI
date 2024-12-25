/**
 * @author Jingren Bai
 * @package moonflowerr.test
 * @since openJDK 22
 */
package moonflowerr.test;

import org.junit.jupiter.api.*;
import xyz.moonflowerr.GomokuWithGUI.Model.Model;
import xyz.moonflowerr.GomokuWithGUI.Var;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ModelTest {
	Model model = new Model();
	@Test
	public void testWinChecker() {
//		model.setChess(0,0, Var.BLACK);
		for (int i = 0; i < 5; i++) {
			model.setChess(i, 0, Var.BLACK);
		}
		assertEquals(1,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(0, i, Var.BLACK);
		}
		assertEquals(1,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(i, i, Var.BLACK);
		}
		assertEquals(1,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(i, 4-i, Var.BLACK);
		}
		assertEquals(1,model.isCurrentPlayerWin(0, 4));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(i, 0, Var.WHITE);
		}
		assertEquals(0,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(0, i, Var.WHITE);
		}
		assertEquals(0,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(i, i, Var.WHITE);
		}
		assertEquals(0,model.isCurrentPlayerWin(0, 0));

		model.clear();
		for (int i = 0; i < 5; i++) {
			model.setChess(i, 4-i, Var.WHITE);
		}
		assertEquals(0,model.isCurrentPlayerWin(0, 4));
	}
}
