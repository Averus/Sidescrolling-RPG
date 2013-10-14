package rpg.sidescrolling.configuration;

import org.apache.commons.lang3.StringUtils;

public class RPGConfigurationService {
	private static RPGConfigurationService rpgConfigurationService;
	
	public int getInt(RPGConfiguration rpgConfiguration) {
		Object value = rpgConfiguration.getValue();
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else {
			try {
				return Integer.parseInt(getString(rpgConfiguration));
			} catch (NumberFormatException e) {
				return -1;
			}
		}
	}

	public short getShort(RPGConfiguration rpgConfiguration) {
		Object value = rpgConfiguration.getValue();
		if (value instanceof Short) {
			return ((Short) value).shortValue();
		} else if (value instanceof Integer) {
			return (short) getInt(rpgConfiguration);
		} else {
			try {
				return Short.parseShort(getString(rpgConfiguration));
			} catch (NumberFormatException e) {
				return -1;
			}
		}
	}

	public String getString(RPGConfiguration rpgConfiguration) {
		Object value = rpgConfiguration.getValue();
		if (rpgConfiguration.getValue() != null) {
			return rpgConfiguration.getValue().toString();
		} else {
			return StringUtils.EMPTY;
		}
	}

	public double getDouble(RPGConfiguration rpgConfiguration) {
		Object value = rpgConfiguration.getValue();
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else {
			try {
				return Double.parseDouble(getString(rpgConfiguration));
			} catch (NumberFormatException e) {
				return -1.00d;
			}
		}
	}
	
	public synchronized static RPGConfigurationService getInstance() {
		if(rpgConfigurationService == null) {
			rpgConfigurationService = new RPGConfigurationService();
		}
		return rpgConfigurationService;
	}
}
