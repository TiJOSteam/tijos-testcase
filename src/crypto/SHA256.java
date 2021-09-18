package crypto;
/**
 * Created by Christian Callau Romero on 27/12/2016.
 * Implementation of the SHA-256 algorithm in java.
 * Source: https://en.wikipedia.org/wiki/SHA-2
 */
public class SHA256 {

  public static byte[] getHash(byte[] text) {

    // Initialize hash values:
    // (first 32 bits of the fractional parts of the square roots of the first 8 primes 2..19):
    int[] H = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};

    // Initialize array of round constants:
    // (first 32 bits of the fractional parts of the cube roots of the first 64 primes 2..311):
    int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    // Pre-processing:
    // Number of 512-bit chunks
    int num = (int) Math.ceil((text.length + 1 + 8) / 64.0);

    int i;
    int[] data = new int[num * 16];
    for (i = 0; i < text.length; i++) {
      data[i / 4] |= (text[i] << (24 - i % 4 * 8));
    }
    // Append the bit '1' to the message
    data[i / 4] |= (0x80 << (24 - i % 4 * 8));

    // Append length of message (without the '1' bit or padding), in bits, as 64-bit big-endian integer
    data[data.length - 2] = (int) Math.floor((text.length * 8) / Math.pow(2.0, 32.0));
    data[data.length - 1] = text.length * 8;

    // Process the message in successive 512-bit chunks:
    for (int n = 0; n < num; n++) {

      // Create a 64-entry message schedule array w[0..63] of 32-bit words
      int[] w = new int[64];

      // Copy chunk into first 16 words w[0..15] of the message schedule array
      for (i = 0; i < 16; i++) {
        w[i] = data[(n * 16) + i];
      }

      // Extend the first 16 words into the remaining 48 words w[16..63] of the message schedule array:
      for (; i < 64; i++) {
        w[i] = w[i - 16] + delta0(w[i - 15]) + w[i - 7] + delta1(w[i - 2]);
      }

      // Initialize working variables to current hash value:
      int a = H[0];
      int b = H[1];
      int c = H[2];
      int d = H[3];
      int e = H[4];
      int f = H[5];
      int g = H[6];
      int h = H[7];

      // Compression function main loop:
      for (i = 0; i < 64; i++) {
        int temp1 = h + sigma1(e) + ch(e, f, g) + k[i] + w[i];
        int temp2 = sigma0(a) + maj(a, b, c);

        h = g;
        g = f;
        f = e;
        e = d + temp1;
        d = c;
        c = b;
        b = a;
        a = temp1 + temp2;
      }

      // Add the compressed chunk to the current hash value:
      H[0] += a;
      H[1] += b;
      H[2] += c;
      H[3] += d;
      H[4] += e;
      H[5] += f;
      H[6] += g;
      H[7] += h;
    }

    // Produce the final hash value (big-endian):
    byte[] hash = new byte[32];
    for (i = 0; i < 8; i++) {
      hash[(i * 4)]     = (byte) (H[i] >>> 24);
      hash[(i * 4) + 1] = (byte) (H[i] >>> 16);
      hash[(i * 4) + 2] = (byte) (H[i] >>>  8);
      hash[(i * 4) + 3] = (byte) (H[i]);
    }

    return hash;
  }

  private static int ror(int a, int n) {
    return (a >>> n) | (a << 32 - n);
  }

  private static int delta0(int a) {
    return ror(a, 7) ^ ror(a, 18) ^ (a >>> 3);
  }

  private static int delta1(int a) {
    return ror(a, 17) ^ ror(a, 19) ^ (a >>> 10);
  }

  private static int sigma0(int a) {
    return ror(a, 2) ^ ror(a, 13) ^ ror(a, 22);
  }

  private static int sigma1(int a) {
    return ror(a, 6) ^ ror(a, 11) ^ ror(a, 25);
  }

  private static int ch(int a, int b, int c) {
    return (a & b) ^ (~a & c);
  }

  private static int maj(int a, int b, int c) {
    return (a & b) ^ (a & c) ^ (b & c);
  }
}