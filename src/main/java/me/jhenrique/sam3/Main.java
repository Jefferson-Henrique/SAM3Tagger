package me.jhenrique.sam3;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

public class Main {

	public static void main(String[] args) {
		String patternInput = (String) JOptionPane.showInputDialog(null, "[1] Track Number\n[2] Artist / Band\n[3] Music Name", "Pattern Filename", JOptionPane.QUESTION_MESSAGE, null, null, "[1] - [2] - [3]");
		
		if (patternInput != null) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fix(patternInput, fileChooser.getSelectedFile());
			}
		}
	}
	
	private static void fix(String patternInput, File directory) {
		int trackNumberGroup = -1;
		int artistGroup = -1;
		int musicNameGroup = -1;
		
		StringBuffer sb = new StringBuffer("^");
		Matcher matcher = Pattern.compile("\\[(\\d+)\\]").matcher(patternInput);
		
		int index = 0;
		while (matcher.find()) {
			Integer refPattern = Integer.valueOf(matcher.group(1));
			PatternsEnum pEnum = PatternsEnum.getPattern(refPattern);
			
			if (pEnum != null) {
				matcher.appendReplacement(sb, pEnum.getRegularExp());
				
				switch (pEnum) {
					case ARTIST_BAND: artistGroup = index; break;
					case MUSIC_NAME: musicNameGroup = index; break;
					case TRACK_NUMBER: trackNumberGroup = index; break;
				}
				
				index++;
			}
		}
		
		sb.append(".mp3$");
		
		if (sb.length() > 2) {
//			TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
//			TagOptionSingleton.getInstance().setLanguage("english");
			
			Pattern patternRecognizer = Pattern.compile(sb.toString());

			File[] files = directory.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith("mp3");
				}
			});
			
			for (File mp3File : files) {
				Matcher matcherRecognizer = patternRecognizer.matcher(mp3File.getName());
				
				String artist = null;
				String musicName = null;
				String trackNumber = null;
				
				if (matcherRecognizer.find()) {
					if (trackNumberGroup > -1) {
						trackNumber = matcherRecognizer.group(1 + trackNumberGroup);
					}
					if (musicNameGroup > -1) {
						musicName = matcherRecognizer.group(1 + musicNameGroup);				
					}
					if (artistGroup > -1) {
						artist = matcherRecognizer.group(1 + artistGroup);
					}
				}
				
				try {
					Mp3FileExtended mp3 = new Mp3FileExtended(mp3File);
					
					ID3v1 tag1 = mp3.getId3v1Tag();
					if (tag1 != null) {
						tag1.setArtist(artist);
						tag1.setTrack(trackNumber);
						tag1.setTitle(musicName);
					}
					
					ID3v2 tag2 = mp3.getId3v2Tag();
					if (tag2 != null) {
						tag2.setArtist(artist);
						tag2.setAlbumArtist(artist);
						tag2.setTrack(trackNumber);
						tag2.setTitle(musicName);
						tag2.setComposer(artist);
						tag2.setCopyright(artist);
						tag2.setOriginalArtist(artist);
						tag2.setPublisher(artist);
					}
					
					if (tag1 != null || tag2 != null) {
						mp3.save();
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}	
}