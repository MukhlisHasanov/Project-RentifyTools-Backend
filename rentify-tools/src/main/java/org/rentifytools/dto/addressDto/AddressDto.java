package org.rentifytools.dto.addressDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private String strasse;
    private String stadt;
    private String plz;
    private String land;
    private String telefonnummer;
}
