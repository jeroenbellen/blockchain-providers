package network.arkane.provider.token;

public class TokenInfoMother {

    private TokenInfoMother() {
    }

    public static TokenInfo.TokenInfoBuilder fnd() {
        return TokenInfo.builder()
                        .name("FundRequest")
                        .symbol("FND")
                        .address("0x4df47b4969b2911c966506e3592c41389493953b")
                        .decimals(18)
                        .type("ERC20");
    }

    public static TokenInfo.TokenInfoBuilder dai() {
        return TokenInfo.builder()
                        .name("Dai Stablecoin v1.0")
                        .symbol("DAI")
                        .address("0x89d24A6b4CcB1B6fAA2625fE562bDD9a23260359")
                        .decimals(18)
                        .type("ERC20");
    }

    public static TokenInfo.TokenInfoBuilder zrx() {
        return TokenInfo.builder()
                        .name("0x Project")
                        .symbol("ZRX")
                        .address("0xE41d2489571d322189246DaFA5ebDe1F4699F498")
                        .decimals(18)
                        .type("ERC20");
    }

    public static TokenInfo.TokenInfoBuilder vtho() {
        return TokenInfo.builder()
                        .name("TokenVeThor")
                        .symbol("VTHO")
                        .address("0x0000000000000000000000000000456E65726779")
                        .decimals(18)
                        .type("ERC20");
    }

    public static TokenInfo.TokenInfoBuilder sha() {
        return TokenInfo.builder()
                        .name("SafeHaven")
                        .symbol("SHA")
                        .address("0x5db3C8A942333f6468176a870dB36eEf120a34DC")
                        .decimals(18)
                        .type("ERC20");
    }
}