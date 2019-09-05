
public class Driver
{

	public static void main(String[] args)
	{
		try
		{
			String string = "";
			int frequency = 500;
			double speed = 1;
			boolean repeat = true;
			
			if (args.length > 3)
			{
				throw new RuntimeException("Too many command line arguments");
			}
			else if (args.length == 3)
			{
				string = args[0];
				frequency = Integer.parseInt(args[1]);
				if (args[3] == "1")
						repeat = true;
			}
			else if (args.length == 2)
			{
				string = args[0];
				frequency = Integer.parseInt(args[1]);
			}
			else
			{
				throw new RuntimeException("Too few command line arguments");
			}
			
			while (true)
			{
				MorseCode.toMorse(string, Waveform.SINE, frequency, speed / 100, repeat);
			}

		} catch (Exception e)
		{
			System.out.println("ERROR: " + e + "!");
		}
	}

}
