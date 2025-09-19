def crc12(data: bytes) -> int:
    """CRC-12: x^12 + x^11 + x^3 + x^2 + x + 1 = 0x180F"""
    crc = 0x000
    poly = 0x180F  # 0001 1000 0000 1111

    for byte in data:
        crc ^= (byte << 4)  # Align data with high end of CRC
        for _ in range(8):
            if crc & 0x800:  # Check 12th bit
                crc = (crc << 1) ^ poly
            else:
                crc <<= 1
            crc &= 0xFFF  # Mask to 12 bits
    return crc


def crc16(data: bytes) -> int:
    """CRC-16-IBM: x^16 + x^15 + x^2 + 1 = 0x8005"""
    crc = 0x0000
    poly = 0x8005

    for byte in data:
        crc ^= (byte << 8)
        for _ in range(8):
            if crc & 0x8000:
                crc = (crc << 1) ^ poly
            else:
                crc <<= 1
            crc &= 0xFFFF  # Mask to 16 bits
    return crc


def crc_ccitt(data: bytes) -> int:
    """CRC-CCITT: x^16 + x^12 + x^5 + 1 = 0x1021"""
    crc = 0xFFFF
    poly = 0x1021

    for byte in data:
        crc ^= (byte << 8)
        for _ in range(8):
            if crc & 0x8000:
                crc = (crc << 1) ^ poly
            else:
                crc <<= 1
            crc &= 0xFFFF  # Mask to 16 bits
    return crc


def main():
    # Input string
    data_str = input("Enter the data string: ").strip()
    data = data_str.encode("utf-8")  # convert to bytes

    # Calculate CRCs
    c12 = crc12(data)
    c16 = crc16(data)
    cccitt = crc_ccitt(data)

    # Print results
    print("\nCRC Results:")
    print(f"CRC-12: 0x{c12:03X} (12 bits)")
    print(f"CRC-16: 0x{c16:04X} (16 bits)")
    print(f"CRC-CCITT: 0x{cccitt:04X} (16 bits)")


if __name__ == "__main__":
    main()
