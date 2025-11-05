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

    // Manual exponent function b/c Math.exp returns a float which is super annoying to deal with
    public static long exp (int base, int n) {
        int ans = 1;
        for (int i = 0; i < n; i++) {
            ans *= base;
        }
        return ans;
    }

    // Convert our bases into base 4 digits for hashing
    public static int basetoIndex(int base) {
        return switch (base) {
            case 'C', 'c' -> 3;
            case 'T', 't' -> 2;
            case 'G', 'g' -> 1;
            case 'A', 'a' -> 0;
            default -> -1;
        };
    }

    // Use horner's method to do the initial hash for strings
    public static long hashString(String sequence, int index, int STRLen, long exp) {
        long hash = 0;
        for (int i = index; i < STRLen + index; i++) {
            // Normally, horner's method would require a modulus to prevent overflow, but because we've optimized our hashing algorithm for base 4 instead of 256, the hash should never overflow, and we don't need the modulus
            hash += (basetoIndex(sequence.charAt(i)) * exp);
            exp /= Radix;
        }
        return hash;
    }

    public static int STRCount(String sequence, String STR) {

        // To prevent repeated .length() and pow calculations
        final int STRLen = STR.length();
        final long pow = exp(Radix, STRLen - 1);

        // Generate hash for STR
        long STRHash = hashString(STR, 0, STRLen, pow);
        long SeqHash = 0;

        int MaxCount = 0;
        int Count = 0;

        // Use two states: matching (false) vs sliding (true)
        boolean matched = false;

        for (int i = 0; i < sequence.length();) {

            if (STRHash == SeqHash) {
                // If we get a match, increment i by STRLen
                // The below loop updates the hash
                for (int j = 0; j < STRLen; j++) {
                    if (i-STRLen + j >= 0) {
                        SeqHash -= (basetoIndex(sequence.charAt(i - STRLen + j)) * pow);
                    }

                    SeqHash *= Radix;

                    SeqHash += basetoIndex(sequence.charAt(i + j));
                }

                i += STRLen;

                // Reset count if it's the start of a "run", otherwise add 1 to count
                if (!matched) {
                    Count = 1;
                }
                else {
                    Count++;
                }

                // Set state to sliding
                matched = true;
            }

            // Hashes don't match
            else {
                // If we were on a "run" and failed to match, go back by STRLen and rehash (to account for testTricky where one run starts during the last)
                if (matched) {
                    matched = false;

                    i -= STRLen;

                    SeqHash = hashString(sequence, i-STRLen, STRLen, pow);
                }

                // Update our hash
                if (i-STRLen >= 0) {
                    SeqHash -= (basetoIndex(sequence.charAt(i - STRLen)) * pow);
                }

                SeqHash *= Radix;

                SeqHash += basetoIndex(sequence.charAt(i));
            }

            // If we are in the matching state, increment i by 1
            if (!matched) {
                i++;
            }

            // Update Maxcount if possible
            if (MaxCount < Count) {
                MaxCount = Count;
            }


        }
        return MaxCount;
    }
}
