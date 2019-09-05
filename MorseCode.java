import java.util.Hashtable;

import javax.sound.sampled.LineUnavailableException;

public class MorseCode
{	

	public static void toMorse(String input, Waveform wave, int frequency, double unit, boolean loop) throws InterruptedException, RuntimeException, LineUnavailableException
	{
		Sound sound = new Sound();
		Hashtable<String, String> table = new Hashtable<String, String>() 
		{
			private static final long serialVersionUID = 6929351403623230669L;

		{ 
			
		put("A", ".- ");    put("B", "-... ");  put("C", "-.-. "); 
		put("D", "-.. ");   put("E", ". ");     put("F", "..-. "); 
		put("G", "--. ");   put("H", ".... ");  put("I", ".. "); 
		put("J", ".--- ");  put("K", "-.- ");   put("L", ".-.. "); 
		put("M", "-- ");    put("N", "-. ");    put("O", "--- "); 
		put("P", ".--. ");  put("Q", "--.- ");  put("R", ".-. "); 
		put("S", "... ");   put("T", "- ");     put("U", "..- "); 
		put("V", "...- ");  put("W", ".-- ");   put("X", "-..- "); 
		put("Y", "-.-- ");  put("Z", "--.. ");  put("0", "----- "); 
		put("1", ".---- "); put("2", "..--- "); put("3", "...-- "); 
		put("4", "....- "); put("5", "..... "); put("6", "-.... "); 
		put("7", "--... "); put("8", "---.. "); put("9", "----. "); 
		put(" ", "$"); }};
		
		input = input.trim();
		input = input.replaceAll("_|[^\\w\\d ]", "");
		input = input.toUpperCase();
		
		String newString = "";
		for (int i = 0; i < input.length(); ++i)
		{
			newString += table.get(String.valueOf(input.charAt(i)));
		}
		newString += "$";
		
		do
		{
			play(sound, newString, wave, frequency, unit);
		} while(loop);
	}

	private static void dot(Sound sound, Waveform wave, int frequency, double unit)
	{
		sound.tone(wave, frequency, unit, 0.15, 1, true);
	}

	private static void dash(Sound sound, Waveform wave, int frequency, double unit)
	{
		sound.tone(wave, frequency, unit * 3, 0.01, 1, true);
	}
	
	private static void play(Sound sound, String string, Waveform wave, int frequency, double unit) throws InterruptedException, RuntimeException, LineUnavailableException
	{
		for (int i = 0; i < string.length(); ++i)
		{
			switch (string.charAt(i))
			{
				case '.':
					dot(sound, wave, frequency, unit);
					Thread.sleep((long) (unit * 1000));
					break;
				case '-':
					dash(sound, wave, frequency, unit);
					Thread.sleep((long) (unit * 1000));
					break;
				case ' ':
					Thread.sleep((long) (3 * unit * 1000));
					break;
				case '$':
					Thread.sleep((long) (4 * unit * 1000));
					break;
				default:
					throw new RuntimeException("Unrecognized character '" + string.charAt(i) + "' in translated string!");
			}
		}
	}
}
