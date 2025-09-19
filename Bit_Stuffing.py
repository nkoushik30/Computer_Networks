def bit_stuffing(input_bits: str) -> str:
    stuffed = ""
    count = 0

    for bit in input_bits:
        stuffed += bit
        if bit == '1':
            count += 1
            if count == 5:
                stuffed += '0'
                count = 0
        else:
            count = 0

    return stuffed


# Main program
if __name__ == "__main__":
    input_bits = input("Enter the bit stream (only 0s and 1s): ")
    result = bit_stuffing(input_bits)
    print("Bit-stuffed output:", result)
