package network.arkane.provider.token;

import lombok.extern.slf4j.Slf4j;
import network.arkane.provider.chain.SecretType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenDiscoveryService {

    private final GithubTokenDiscoveryService githubTokenDiscoveryService;
    private final Map<SecretType, ? extends NativeTokenDiscoveryService> tokenDiscoveryServices;

    public TokenDiscoveryService(final GithubTokenDiscoveryService githubTokenDiscoveryService,
                                 final List<? extends NativeTokenDiscoveryService> tokenDiscoveryServices) {

        this.githubTokenDiscoveryService = githubTokenDiscoveryService;
        this.tokenDiscoveryServices = tokenDiscoveryServices.stream().collect(Collectors.toMap(NativeTokenDiscoveryService::type, Function.identity()));
    }

    public List<TokenInfo> getTokens(final SecretType chain) {
        Map<String, TokenInfo> tokens = githubTokenDiscoveryService.getTokens().get(chain);
        return new ArrayList<>(tokens.values());
    }

    @Cacheable(value = "tokenInfo")
    public Optional<TokenInfo> getTokenInfo(final SecretType chain, final String tokenAddress) {
        final Optional<TokenInfo> tokenInfo = Optional.ofNullable(githubTokenDiscoveryService.getTokens().get(chain)).map(t -> t.get(tokenAddress));
        return tokenInfo.isPresent() ? tokenInfo : fetchFromChain(chain, tokenAddress);
    }

    private Optional<TokenInfo> fetchFromChain(SecretType chain, String tokenAddress) {
        try {
            return tokenDiscoveryServices.get(chain).getTokenInfo(tokenAddress);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
