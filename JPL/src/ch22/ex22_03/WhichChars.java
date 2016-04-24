/* Copyright (C) 2016 Ken Miura */
package ch22.ex22_03;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Ken Miura
 *
 */
public class WhichChars {

	Map<Byte, BitSet> characterMap = new HashMap<>();
	
	public WhichChars (String str) {
		char[] chars = str.toCharArray();
		for (int i=0; i<chars.length; i++) {
			characterMap.put(createHighBit(chars[i]), createLowBitSet(chars[i]));
		}
		System.out.println("");
	}

	private byte createHighBit(char c) {
		int high = c & 0x0000FF00;
		high = high >>> 8;
		return (byte) high;
	}

	private BitSet createLowBitSet(char c) {
		int low = c & 0x000000FF;
		BitSet bitSet = new BitSet(8);
		for (int i=0; i<8; i++) {
			int result = low & 0x00000001;
			if (result>0) {
				bitSet.set(i);
			}
			low >>>= 1;
		}
		return bitSet;
	}	
	
	@Override
	public String toString(){
		StringBuilder desc = new StringBuilder("[");
		Set<Entry<Byte, BitSet>> entries = characterMap.entrySet();
		for (Entry<Byte, BitSet> entry: entries) {
			int temp = entry.getKey();
			int high = temp << 8;
			int low = decodeBitSet(entry.getValue());
			char c = (char) (high | low);
			desc.append(c);
		}
		return desc.append("]").toString();
	}

	private int decodeBitSet(BitSet value) {
		double temp = 0.0;
		for (int i=value.nextSetBit(0); i>=0; i=value.nextSetBit(i+1)) {
			temp += Math.pow(2, i);
		}
		return (int) temp;
	}
}
