package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.scality.vaultclient.services.VaultClientException;
import com.scality.vaultclient.utils.VaultClientUtils;

/**
 * The type List accounts request marshaller extends GenericRequestMarshaller to marshall List Account Requests.
 */
public class ListAccountsRequestMarshaller extends GenericRequestMarshaller<ListAccountsRequestDTO>{

    private static final MarshallingInfo<String> MARKER_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("Marker").build();
    private static final MarshallingInfo<Integer> MAX_ITEMS_BINDING = MarshallingInfo.builder(MarshallingType.INTEGER).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("MaxItems").build();
    private static final MarshallingInfo<String> FILTER_KEY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("filterKey").build();
    private static final MarshallingInfo<String> FILTER_KEY_STARTS_WITH_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("filterKeyStartsWith").build();


    private static final String LIST_ACCOUNTS_ACTION = "ListAccounts";

    private static final ListAccountsRequestMarshaller instance = new ListAccountsRequestMarshaller();

    public static ListAccountsRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with listAccountsRequestDTO parameters to marshall.
     *
     * @param listAccountsRequestDTO         the request dto of type ListAccountsRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(ListAccountsRequestDTO listAccountsRequestDTO, ProtocolRequestMarshaller<ListAccountsRequestDTO> protocolMarshaller) {

        super.marshall(listAccountsRequestDTO, protocolMarshaller, LIST_ACCOUNTS_ACTION);

        try {
            if (listAccountsRequestDTO.getMaxItems() < 0 || listAccountsRequestDTO.getMaxItems() > 1000) {
                throw new VaultClientException("maxItems need to be a value between 1 and 1000 included");
            } else if (listAccountsRequestDTO.getMaxItems() > 0) {
                protocolMarshaller.marshall(listAccountsRequestDTO.getMaxItems(), MAX_ITEMS_BINDING);
            }

            if (listAccountsRequestDTO.getMarker()!=null) {

                if (StringUtils.isNullOrEmpty(listAccountsRequestDTO.getMarker())
                        || VaultClientUtils.isStringInteger(listAccountsRequestDTO.getMarker())) {
                    throw new VaultClientException("invalid marker supplied.");
                }

                protocolMarshaller.marshall(listAccountsRequestDTO.getMarker(), MARKER_BINDING);
            }

            if (!StringUtils.isNullOrEmpty(listAccountsRequestDTO.getFilterKey())) {

                if (!StringUtils.isNullOrEmpty(listAccountsRequestDTO.getFilterKeyStartsWith())) {
                    throw new VaultClientException("filterKey and filterKeyStartsWith parameters are exclusive");
                }

                protocolMarshaller.marshall(listAccountsRequestDTO.getFilterKey(), FILTER_KEY_BINDING);

            } else if (!StringUtils.isNullOrEmpty(listAccountsRequestDTO.getFilterKeyStartsWith())) {
                protocolMarshaller.marshall(listAccountsRequestDTO.getFilterKeyStartsWith(), FILTER_KEY_STARTS_WITH_BINDING);
            }

        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
