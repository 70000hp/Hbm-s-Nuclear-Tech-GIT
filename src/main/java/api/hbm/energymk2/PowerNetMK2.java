package api.hbm.energymk2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import api.hbm.energymk2.Nodespace.PowerNode;

public class PowerNetMK2 {
	
	public boolean valid = true;
	public Set<PowerNode> links = new HashSet();

	/** Maps all active subscribers to a timestamp, handy for handling timeouts. In a good system this shouldn't be necessary, but the previous system taught me to be cautious anyway */
	public HashMap<IEnergyReceiverMK2, Long> receiverEntries = new HashMap();
	public HashMap<IEnergyProviderMK2, Long> providerEntries = new HashMap();
	
	public PowerNetMK2() {
		Nodespace.activePowerNets.add(this);
	}

	/// SUBSCRIBER HANDLING ///
	public boolean isSubscribed(IEnergyReceiverMK2 receiver) {
		return this.receiverEntries.containsKey(receiver);
	}

	public void addReceiver(IEnergyReceiverMK2 receiver) {
		this.receiverEntries.put(receiver, System.currentTimeMillis());
	}

	public void removeReceiver(IEnergyReceiverMK2 receiver) {
		this.receiverEntries.remove(receiver);
	}

	/// PROVIDER HANDLING ///
	public boolean isProvider(IEnergyProviderMK2 provider) {
		return this.providerEntries.containsKey(provider);
	}

	public void addProvider(IEnergyProviderMK2 provider) {
		this.providerEntries.put(provider, System.currentTimeMillis());
	}

	public void removeProvider(IEnergyProviderMK2 provider) {
		this.providerEntries.remove(provider);
	}
	
	/// LINK JOINING ///
	
	/** Combines two networks into one */
	public void joinNetworks(PowerNetMK2 network) {
		
		if(network == this) return; //wtf?!

		List<PowerNode> oldNodes = new ArrayList(network.links.size());
		oldNodes.addAll(network.links); // might prevent oddities related to joining - nvm it does nothing
		
		for(PowerNode conductor : oldNodes) forceJoinLink(conductor);
		network.links.clear();

		for(IEnergyReceiverMK2 connector : network.receiverEntries.keySet()) this.addReceiver(connector);
		for(IEnergyProviderMK2 connector : network.providerEntries.keySet()) this.addProvider(connector);
		network.destroy();
	}

	/** Adds the power node as part of this network's links */
	public PowerNetMK2 joinLink(PowerNode node) {
		if(node.net != null) node.net.leaveLink(node);
		return forceJoinLink(node);
	}

	/** Adds the power node as part of this network's links, skips the part about removing it from existing networks */
	public PowerNetMK2 forceJoinLink(PowerNode node) {
		this.links.add(node);
		node.setNet(this);
		return this;
	}

	/** Removes the specified power node */
	public void leaveLink(PowerNode node) {
		node.setNet(null);
		this.links.remove(node);
	}
	
	/// GENERAL POWER NET CONTROL ///
	public void invalidate() {
		this.valid = false;
		Nodespace.activePowerNets.remove(this);
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	public void destroy() {
		this.invalidate();
		for(PowerNode link : this.links) if(link.net == this) link.setNet(null);
		this.links.clear();
		this.receiverEntries.clear();
		this.providerEntries.clear();
	}
}
