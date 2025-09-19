from typing import Optional
def calculate_checksum(data: str) -> int:
    """Calculate checksum as sum of bytes modulo 256"""
    checksum = sum(bytearray(data, "utf-8")) % 256
    return checksum

def send_frame(data: str) -> bytes:
    """Create frame with SOF, data, checksum, and EOF"""
    SOF = 0x7E
    EOF = 0x7F
    checksum = calculate_checksum(data)

    data_bytes = data.encode("utf-8")
    frame = bytearray()
    frame.append(SOF)
    frame.extend(data_bytes)
    frame.append(checksum)
    frame.append(EOF)

    return bytes(frame)

def receive_frame(frame: bytes) -> Optional[str]:
    """Check framing and checksum at receiver side"""
    SOF = 0x7E
    EOF = 0x7F

    if frame[0] != SOF or frame[-1] != EOF:
        print("Frame error: Invalid framing bytes.")
        return None

    data_bytes = frame[1:-2]
    received_checksum = frame[-2]
    data = data_bytes.decode("utf-8")

    calc_checksum = calculate_checksum(data)

    if received_checksum != calc_checksum:
        print("Checksum error!")
        return None

    return data

def main():
    data = input("Enter data to send: ")

    frame = send_frame(data)
    print("Transmitted Frame (in hex):", " ".join(f"{b:02X}" for b in frame))

    received_data = receive_frame(frame)
    if received_data is not None:
        print("Received data:", received_data)
        print("No error detected in frame.")

if __name__ == "__main__":
    main()
