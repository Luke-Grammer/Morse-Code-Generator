
import javax.sound.sampled.*;

enum Waveform
{
	SINE, SQUARE, TRIANGLE, SAWTOOTH
};

public class Sound
{
	private static final double SAMPLE_RATE = 4400.0;

	private AudioFormat format;
	private SourceDataLine out;
	private Graph g;

	public Sound() throws LineUnavailableException
	{
		format = new AudioFormat((float) SAMPLE_RATE, 8, 1, true, false);
		out = AudioSystem.getSourceDataLine(format);
		out.open(format);
	}

	public void tone(Waveform waveform, int frequency, double time, double fadeTime, double vol, boolean graph)
	{
		if (graph)
			g = new Graph();
		byte[] buf = new byte[1];
		out.start();
		switch (waveform)
		{
			case SINE:
				sineTone(buf, frequency, time, vol, graph);
				sineFade(buf, frequency, fadeTime, vol);
				break;
			case SQUARE:
				squareTone(buf, frequency, time, vol, graph);
				squareFade(buf, frequency, fadeTime, vol);
				break;
			case TRIANGLE:
				triangleTone(buf, frequency, time, vol, graph);
				triangleFade(buf, frequency, fadeTime, vol);
				break;
			case SAWTOOTH:
				sawtoothTone(buf, frequency, time, vol, graph);
				sawtoothFade(buf, frequency, fadeTime, vol);
				break;
			default:
				throw new RuntimeException("Unrecognized waveform choice!");
		}
		out.drain();
		out.flush();
		out.stop();
	}

	private void sineTone(byte[] buf, int frequency, double time, double vol, boolean graph)
	{
		for (int i = 0; i < time * SAMPLE_RATE; i++)
		{
			buf[0] = (byte) (Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI) * 127 * vol);
			if (graph)
				g.GraphLine(i, buf[0]);
			out.write(buf, 0, 1);
		}
	}

	private void sineFade(byte[] buf, int frequency, double fadeTime, double vol)
	{
		for (int i = 0; i < fadeTime * SAMPLE_RATE; i++)
		{
			vol = vol - (1 / (fadeTime * SAMPLE_RATE));
			buf[0] = (byte) (Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI) * 127 * vol);
			out.write(buf, 0, 1);
		}
	}

	private void squareTone(byte[] buf, int frequency, double time, double vol, boolean graph)
	{
		for (int i = 0; i < time * SAMPLE_RATE; i++)
		{
			if (Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI) <= 0)
				buf[0] = (byte) (-127 * vol);
			else
				buf[0] = (byte) (127 * vol);
			if (graph)
				g.GraphLine(i, buf[0]);
			out.write(buf, 0, 1);
		}
	}

	private void squareFade(byte[] buf, int frequency, double fadeTime, double vol)
	{
		for (int i = 0; i < fadeTime * SAMPLE_RATE; i++)
		{
			vol = vol - (1 / (fadeTime * SAMPLE_RATE));
			if (Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI) <= 0)
				buf[0] = (byte) (-127 * vol);
			else
				buf[0] = (byte) (127 * vol);
			out.write(buf, 0, 1);
		}
	}

	private void triangleTone(byte[] buf, int frequency, double time, double vol, boolean graph)
	{
		for (int i = 0; i < time * SAMPLE_RATE; i++)
		{
			buf[0] = (byte) (Math.asin(Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI))
					* ((127 * vol * 2) / Math.PI));
			if (graph)
				g.GraphLine(i, buf[0]);
			out.write(buf, 0, 1);
		}
	}

	private void triangleFade(byte[] buf, int frequency, double fadeTime, double vol)
	{
		for (int i = 0; i < fadeTime * SAMPLE_RATE; i++)
		{
			vol = vol - (1 / (fadeTime * SAMPLE_RATE));
			buf[0] = (byte) (Math.asin(Math.sin(i / (SAMPLE_RATE / frequency) * 2 * Math.PI))
					* ((127 * vol * 2) / Math.PI));
			out.write(buf, 0, 1);
		}
	}

	private void sawtoothTone(byte[] buf, int frequency, double time, double vol, boolean graph)
	{

		for (int i = 0; i < time * SAMPLE_RATE; i++)
		{
			buf[0] = (byte) (Math.atan(Math.tan(i / (SAMPLE_RATE / frequency) * Math.PI))
					* ((127 * vol * 2) / Math.PI));
			if (graph)
				g.GraphLine(i, buf[0]);
			out.write(buf, 0, 1);
		}
	}

	private void sawtoothFade(byte[] buf, int frequency, double fadeTime, double vol)
	{
		for (int i = 0; i < fadeTime * SAMPLE_RATE; i++)
		{
			vol = vol - (1 / (fadeTime * SAMPLE_RATE));
			buf[0] = (byte) (Math.atan(Math.tan(i / (SAMPLE_RATE / frequency) * Math.PI))
					* ((127 * vol * 2) / Math.PI));
			out.write(buf, 0, 1);
		}
	}
}