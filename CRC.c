CRC-12: x¹² + x¹¹ + x³ + x² + x + 1 (poly: 0x180F, used e.g. in SDLC)
● CRC-16: x¹⁶ + x¹⁵ + x² + 1 (poly: 0x8005, common CRC-16-IBM)
● CRC-CCITT (CRC-16-CCITT): x¹⁶ + x¹² + x⁵ + 1 (poly: 0x1021)
Program:
#include <stdio.h>
#include <stdint.h>
#include <string.h>
// initial CRC values
#define CRC16_INIT 0x0000
#define CRCCCITT_INIT 0xFFFF
#define CRC12_INIT 0x000
// CRC-12: x^12 + x^11 + x^3 + x^2 + x + 1 = 0x180F
uint16_t crc12(const uint8_t *data, size_t len) {
uint16_t crc = CRC12_INIT;
uint16_t poly = 0x180F; // 0001 1000 0000 1111
for (size_t i = 0; i < len; i++) {
crc ^= data[i] << 4; // Align data with high end of crc (data is 8 bits, CRC is 12 bits)
for (int j = 0; j < 8; j++) {
if (crc & 0x800) // If highest (12th) bit is set
crc = (crc << 1) ^ poly;
else
crc <<= 1;
crc &= 0xFFF; // Mask to 12 bits
}
}
return crc;
}
// CRC-16: x^16 + x^15 + x^2 + 1 = 0x8005 (CRC-16-IBM)
uint16_t crc16(const uint8_t *data, size_t len) {
uint16_t crc = CRC16_INIT;
uint16_t poly = 0x8005; // 1000 0000 0000 0101
for (size_t i = 0; i < len; i++) {
crc ^= data[i] << 8;
for (int j = 0; j < 8; j++) {
if (crc & 0x8000)
crc = (crc << 1) ^ poly;
else
crc <<= 1;
}
}
return crc;
}
// CRC-CCITT: x^16 + x^12 + x^5 + 1 = 0x1021
uint16_t crc_ccitt(const uint8_t *data, size_t len) {
uint16_t crc = CRCCCITT_INIT;
uint16_t poly = 0x1021;
for (size_t i = 0; i < len; i++) {
crc ^= data[i] << 8;
for (int j = 0; j < 8; j++) {
if (crc & 0x8000)
crc = (crc << 1) ^ poly;
else
crc <<= 1;
}
}
return crc;
}
int main() {
char input[128];
printf("Enter the data string: ");
scanf("%127s", input);
size_t len = strlen(input);
uint16_t c12 = crc12((uint8_t*)input, len);
uint16_t c16 = crc16((uint8_t*)input, len);
uint16_t cccitt = crc_ccitt((uint8_t*)input, len);
printf("\nCRC Results:\n");
printf("CRC-12: 0x%03X (12 bits)\n", c12);
printf("CRC-16: 0x%04X (16 bits)\n", c16);
printf("CRC-CCITT: 0x%04X (16 bits)\n", cccitt);
return 0;
}
Output 1:
Enter the data string: 1101011011
CRC Results:
CRC-12: 0xD47 (12 bits)
CRC-16: 0x0496 (16 bits)
CRC-CCITT: 0x9DEB (16 bits)
