/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Lucas Wang
 *</p>
 */

public class DNA {
    public static final int Radix = 4;
    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */

    public static long exp (int base, int n) {
        int ans = 1;
        for (int i = 0; i < n; i++) {
            ans *= base;
        }
        return ans;
    }

    public static int basetoIndex(int base) {
        return switch (base) {
            case 'C', 'c' -> 3;
            case 'T', 't' -> 2;
            case 'G', 'g' -> 1;
            case 'A', 'a' -> 0;
            default -> -1;
        };
    }

    public static long hashString(String sequence, int index, int STRLen) {
        long hash = 0;
        for (int i = index; i < STRLen + index; i++) {
            hash += (basetoIndex(sequence.charAt(i)) * exp(Radix, STRLen + index - i - 1));
        }
        return hash;
    }
    public static int STRCount(String sequence, String STR) {

        int STRLen = STR.length();
        final long Radixexp = exp(Radix, STRLen - 1);
        // generate hash for STR
        long STRHash = hashString(STR, 0, STRLen);
        long SeqHash = 0;

        int MaxCount = 0;
        int Count = 0;

        // use two states: matching (false) vs sliding (true)

        boolean matched = false;

        for (int i = 0; i < sequence.length();) {
//            System.out.println(SeqHash);

            // if hash matches

            if (STRHash == SeqHash) {
                i += STRLen;
                SeqHash = hashString(sequence, i - STRLen,  STRLen);

                if (!matched) {
                    Count = 1;
                }
                else {
                    Count++;
                }

                matched = true;
            }

            // not found
            else {
                if (matched) {
                    matched = false;
                    i -= STRLen;
                    SeqHash = hashString(sequence, i-STRLen, STRLen);
//                    System.out.println(SeqHash + '\n');

                }
                if (i-STRLen >= 0) {
                    SeqHash -= (basetoIndex(sequence.charAt(i - STRLen)) * Radixexp);
                }

                SeqHash *= Radix;

                SeqHash += basetoIndex(sequence.charAt(i));
            }

            if (!matched) {
                i++;
            }

            if (MaxCount < Count) {
                MaxCount = Count;
            }


        }

        return MaxCount;
    }
}
