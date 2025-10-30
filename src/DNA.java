
/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: [YOUR NAME HERE]
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
            case 'C' -> 3;
            case 'T' -> 2;
            case 'G' -> 1;
            case 'A' -> 0;
            case 'c' -> 3;
            case 't' -> 2;
            case 'g' -> 1;
            case 'a' -> 0;
            default -> -1;
        };
    }

    public static long hashString(String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash += (basetoIndex(str.charAt(i)) * exp(Radix, str.length() - i - 1));
        }
        return hash;
    }
    public static int STRCount(String sequence, String STR) {
        int STRLen = STR.length();
        // generate hash for STR
        long STRHash = hashString(STR);
        long SeqHash = 0;

        int MaxCount = 0;
        int Count = 0;

        // use two states: matching (false) vs sliding (true)

        boolean matched = false;

        for (int i = 0; i < sequence.length();) {
//            System.out.println(SeqHash);
//            System.out.println(sequence.substring(max(i - STRLen, 0), min(i,sequence.length())));
            // if hash matches

            if (STRHash == SeqHash) {
                i += STRLen;
                SeqHash = hashString(sequence.substring(i - STRLen, i));

                if (!matched) {
                    Count = 1;
                }
                else {
                    Count++;
                }

//                System.out.println("Count = " + Count);
                matched = true;
            }

            // not found
            else {
                if (matched) {
//                    System.out.println("back to matching");
                    matched = false;
                    i -=  STRLen;
//                    System.out.println(sequence.substring(i - STRLen, i));
                    SeqHash = hashString(sequence.substring(i-STRLen, i));

                }
                if (i-STRLen > 0) {
                    SeqHash -= (basetoIndex(sequence.charAt(i - STRLen)) * exp(Radix, STRLen - 1));
                }

//            System.out.println(SeqHash);
                SeqHash *= Radix;
//            System.out.println(SeqHash);
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
