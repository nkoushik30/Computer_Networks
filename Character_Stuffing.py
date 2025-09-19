def character_stuffing():
    # Input Section
    data = input("Enter the data to be stuffed: ")

    start_delim = input("Enter the starting delimiter character: ")[0]
    end_delim = input("Enter the ending delimiter character: ")[0]

    # Prepare doubled delimiters
    double_start = start_delim * 2
    double_end = end_delim * 2

    # Start framing with starting delimiter
    stuffed = double_start

    # Stuffing logic
    for ch in data:
        if ch == start_delim:
            stuffed += double_start
        elif ch == end_delim:
            stuffed += double_end
        else:
            stuffed += ch

    # Add ending delimiter
    stuffed += double_end

    # Output
    print("Data after character stuffing:", stuffed)


# Run the function
if __name__ == "__main__":
    character_stuffing()
