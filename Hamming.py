def generate_hamming_code(data_bits):
    """Generate 7-bit Hamming code from 4 data bits (even parity)."""
    hcode = [0] * 7

    # Assign data bits (positions: 1 2 3 4 5 6 7 => P1, P2, D1, P4, D2, D3, D4)
    hcode[2] = data_bits[0]  # D1
    hcode[4] = data_bits[1]  # D2
    hcode[5] = data_bits[2]  # D3
    hcode[6] = data_bits[3]  # D4

    # Calculate parity bits (even parity)
    hcode[0] = hcode[2] ^ hcode[4] ^ hcode[6]  # P1
    hcode[1] = hcode[2] ^ hcode[5] ^ hcode[6]  # P2
    hcode[3] = hcode[4] ^ hcode[5] ^ hcode[6]  # P4

    return hcode


def detect_and_correct(hcode):
    """Detect and correct single-bit errors in Hamming code."""
    # Parity checks
    p1 = hcode[0] ^ hcode[2] ^ hcode[4] ^ hcode[6]
    p2 = hcode[1] ^ hcode[2] ^ hcode[5] ^ hcode[6]
    p4 = hcode[3] ^ hcode[4] ^ hcode[5] ^ hcode[6]

    # Binary -> decimal (position of error)
    error_pos = p4 * 4 + p2 * 2 + p1 * 1

    if error_pos == 0:
        print("No error detected in received code.")
    else:
        print(f"Error detected at bit position {error_pos} (counting from 1).")
        # Correct error
        hcode[error_pos - 1] ^= 1
        print("Corrected code:", " ".join(map(str, hcode)))

    return hcode


def main():
    # Input 4 data bits
    data_bits = list(map(int, input("Enter 4 data bits (space-separated): ").split()))
    if len(data_bits) != 4:
        print("Please enter exactly 4 bits.")
        return

    # Encode
    hcode = generate_hamming_code(data_bits)
    print("Generated 7-bit Hamming code:", " ".join(map(str, hcode)))

    # Optional error simulation
    opt = input("Simulate error? (y/n): ").strip().lower()
    if opt == 'y':
        pos = int(input("Enter bit position to flip (1-7): "))
        if 1 <= pos <= 7:
            hcode[pos - 1] ^= 1
            print("Codeword after error:", " ".join(map(str, hcode)))

    # Detect and correct
    corrected_code = detect_and_correct(hcode)

    # Extract data bits
    data_extracted = [corrected_code[2], corrected_code[4], corrected_code[5], corrected_code[6]]
    print("Extracted data bits:", " ".join(map(str, data_extracted)))


if __name__ == "__main__":
    main()
