package network.arkane.provider.bitcoin.unspent;

import network.arkane.provider.bitcoin.BitcoinEnv;
import network.arkane.provider.blockcypher.BlockcypherGateway;
import network.arkane.provider.blockcypher.domain.BlockcypherAddressUnspents;
import network.arkane.provider.blockcypher.domain.BlockcypherTransactionRef;
import org.bitcoinj.core.Address;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnspentService {

    private BlockcypherGateway blockcypherGateway;
    private BitcoinEnv bitcoinEnv;

    public UnspentService(BlockcypherGateway blockcypherGateway, BitcoinEnv bitcoinEnv) {
        this.blockcypherGateway = blockcypherGateway;
        this.bitcoinEnv = bitcoinEnv;
    }

    public List<Unspent> getUnspentForAddress(final Address address) {
        BlockcypherAddressUnspents unspentTransactions = blockcypherGateway.getUnspentTransactions(bitcoinEnv.getNetwork(), address.toBase58());
        if (unspentTransactions == null || unspentTransactions.getTransactionRefs() == null || unspentTransactions.getTransactionRefs().size() == 0) {
            return new ArrayList<>();
        }
        return unspentTransactions
                .getTransactionRefs()
                .stream()
                .map(this::toUnspent)
                .collect(Collectors.toList());
    }

    private Unspent toUnspent(BlockcypherTransactionRef transactionRef) {
        return Unspent.builder()
                      .amount(transactionRef.getValue().longValue())
                      .txId(transactionRef.getTransactionHash())
                      .vOut(transactionRef.getTransactionOutputN().longValue())
                      .scriptPubKey(transactionRef.getScript())
                      .build();
    }
}
