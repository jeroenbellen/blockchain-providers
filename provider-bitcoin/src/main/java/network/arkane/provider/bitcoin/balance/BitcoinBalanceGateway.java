package network.arkane.provider.bitcoin.balance;

import network.arkane.provider.PrecisionUtil;
import network.arkane.provider.balance.BalanceGateway;
import network.arkane.provider.balance.domain.Balance;
import network.arkane.provider.balance.domain.TokenBalance;
import network.arkane.provider.bitcoin.BitcoinEnv;
import network.arkane.provider.blockcypher.BlockcypherGateway;
import network.arkane.provider.blockcypher.domain.BlockcypherAddress;
import network.arkane.provider.chain.SecretType;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class BitcoinBalanceGateway implements BalanceGateway {

    private BlockcypherGateway blockcypherGateway;
    private BitcoinEnv bitcoinEnv;

    public BitcoinBalanceGateway(BlockcypherGateway blockcypherGateway, BitcoinEnv bitcoinEnv) {
        this.blockcypherGateway = blockcypherGateway;
        this.bitcoinEnv = bitcoinEnv;
    }

    @Override
    public Balance getBalance(String address) {
        BlockcypherAddress balance = blockcypherGateway.getBalance(bitcoinEnv.getNetwork(), address);
        BigInteger confirmedBalance = balance.getBalance();
        return Balance.builder()
                      .decimals(8)
                      .gasBalance(confirmedBalance == null ? 0 : PrecisionUtil.toDecimal(confirmedBalance, 8))
                      .balance(confirmedBalance == null ? 0 : PrecisionUtil.toDecimal(confirmedBalance, 8))
                      .rawGasBalance(confirmedBalance == null ? "0" : confirmedBalance.toString())
                      .rawBalance(confirmedBalance == null ? "0" : confirmedBalance.toString())
                      .secretType(SecretType.BITCOIN)
                      .gasSymbol("BTC")
                      .symbol("BTC")
                      .build();
    }

    @Override
    public TokenBalance getTokenBalance(String address, String tokenAddress) {
        throw new UnsupportedOperationException("Not possible for bitcoin");
    }

    @Override
    public List<TokenBalance> getTokenBalances(String address) {
        throw new UnsupportedOperationException("Not possible for bitcoin");
    }

    @Override
    public SecretType type() {
        return SecretType.BITCOIN;
    }
}
