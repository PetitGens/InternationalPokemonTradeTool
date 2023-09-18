package test.tradingEngine.gameData.strings;

import main.java.tradingEngine.gameData.strings.InGameString;
import main.java.tradingEngine.gameData.strings.InGameWesternCharacter;
import main.java.tradingEngine.gameData.strings.WesternString;
import org.junit.Test;

import static org.junit.Assert.*;

public class InGameStringTest {
	@Test
	public void stringLengthTest(){
		byte[] validArray = {
				InGameWesternCharacter.CAPITAL_A.value,
				InGameWesternCharacter.CAPITAL_B.value,
				InGameWesternCharacter.CAPITAL_C.value,
				InGameWesternCharacter.CAPITAL_D.value,
				InGameWesternCharacter.CAPITAL_E.value,
				InGameWesternCharacter.CAPITAL_F.value,
				InGameWesternCharacter.CAPITAL_G.value,
				InGameWesternCharacter.CAPITAL_H.value,
				InGameWesternCharacter.CAPITAL_I.value,
				InGameWesternCharacter.CAPITAL_J.value,
				InGameWesternCharacter.DELIMITER.value
		};
		assertEquals(10, InGameString.stringLength(validArray));

		byte[] machopCharacters = {
				InGameWesternCharacter.CAPITAL_M.value,
				InGameWesternCharacter.CAPITAL_A.value,
				InGameWesternCharacter.CAPITAL_C.value,
				InGameWesternCharacter.CAPITAL_H.value,
				InGameWesternCharacter.CAPITAL_O.value,
				InGameWesternCharacter.CAPITAL_P.value,
		};
		assertEquals(6, InGameString.stringLength(machopCharacters));

		byte[] smallArray = {InGameWesternCharacter.CAPITAL_A.value};
		assertEquals(1, InGameString.stringLength(smallArray));

		byte[] emptyArray = {(byte) 0x50};
		assertEquals(0, InGameString.stringLength(emptyArray));
	}
	
	@Test
	public void toArrayTest() {
		byte[] validArray = {
				InGameWesternCharacter.CAPITAL_A.value,
				InGameWesternCharacter.CAPITAL_B.value,
				InGameWesternCharacter.CAPITAL_C.value,
				InGameWesternCharacter.CAPITAL_D.value,
				InGameWesternCharacter.CAPITAL_E.value,
				InGameWesternCharacter.CAPITAL_F.value,
				InGameWesternCharacter.CAPITAL_G.value,
				InGameWesternCharacter.CAPITAL_H.value,
				InGameWesternCharacter.CAPITAL_I.value,
				InGameWesternCharacter.CAPITAL_J.value,
				InGameWesternCharacter.DELIMITER.value
		};

		WesternString westernString = new WesternString();

		westernString.addAll(validArray);

		assertArrayEquals(validArray, westernString.toArray());
		

		byte[] machopCharacters = {
				InGameWesternCharacter.CAPITAL_M.value,
				InGameWesternCharacter.CAPITAL_A.value,
				InGameWesternCharacter.CAPITAL_C.value,
				InGameWesternCharacter.CAPITAL_H.value,
				InGameWesternCharacter.CAPITAL_O.value,
				InGameWesternCharacter.CAPITAL_P.value,
				InGameWesternCharacter.DELIMITER.value,
		};
		
		byte[] machopCharacters2 = new byte[11];

		// Copying array
		System.arraycopy(machopCharacters, 0, machopCharacters2, 0, machopCharacters.length);

		westernString.addAll(machopCharacters);

		assertArrayEquals(machopCharacters2, westernString.toArray());

		byte[] smallArray = {InGameWesternCharacter.CAPITAL_A.value, (byte) 0x50};
		
		byte[] smallArray2 = new byte[11];

		// Copying array
		System.arraycopy(smallArray, 0, smallArray2, 0, smallArray.length);

		westernString.addAll(smallArray);

		assertArrayEquals(smallArray2, westernString.toArray());
	}
}