package com.gentics.mesh.etc.config;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.gentics.mesh.doc.GenerateDocumentation;

/**
 * POJO for the mesh cluster options
 */
@GenerateDocumentation
public class ClusterOptions {

	public static final boolean ENABLED = true;
	public static final boolean DISABLED = false;

	public static final boolean DEFAULT_CLUSTER_MODE = DISABLED;

	@JsonProperty(required = false)
	@JsonPropertyDescription("IP or host which is used to announce and reach the instance in the cluster. Gentics Mesh will try to determine the IP automatically but you may use this setting to override this automatic IP handling.")
	private String networkHost;

	// TODO use public and bind host - https://www.prjhub.com/#/issues/9058, https://www.elastic.co/guide/en/elasticsearch/reference/2.0/modules-network.html
	// private String publicHost;
	// private String bindHost;

	@JsonProperty(required = false)
	@JsonPropertyDescription("Flag to enable or disable the cluster mode. (Not yet fully implemented)")
	private boolean enabled = DEFAULT_CLUSTER_MODE;

	@JsonProperty(required = false)
	@JsonPropertyDescription("Name of the cluster. Only instances with a common cluster name will form a cluster.")
	private String clusterName;

	@JsonProperty(required = false)
	@JsonPropertyDescription("Port used by Vert.x for the eventbus server. A random free port will be selected if set to 0.")
	private Integer vertxPort = 0;

	/**
	 * Return the cluster enabled flag.
	 * 
	 * @return Flag value
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set the flag which can toggle the cluster mode.
	 * 
	 * @param flag
	 *            Flag value
	 * @return Fluent API
	 */
	public ClusterOptions setEnabled(boolean flag) {
		this.enabled = flag;
		return this;
	}

	/**
	 * Return the configured network host which is used to bind all mesh related internal services to that IP.
	 * 
	 * @return
	 */
	public String getNetworkHost() {
		return networkHost;
	}

	/**
	 * Set the network host which is used to bind to.
	 * 
	 * @param networkHost
	 */
	public void setNetworkHost(String networkHost) {
		this.networkHost = networkHost;
	}

	/**
	 * Return the configured cluster name.
	 * 
	 * @return
	 */
	public String getClusterName() {
		return clusterName;
	}

	/**
	 * Set the cluster name to be used.
	 * 
	 * @param clusterName
	 */
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/**
	 * Validate the options.
	 * 
	 * @param meshOptions
	 */
	public void validate(MeshOptions meshOptions) {
		if (isEnabled()) {
			Objects.requireNonNull(getClusterName(), "No cluster.clusterName was specified within mesh options.");
			Objects.requireNonNull(meshOptions.getNodeName(), "No nodeName was specified within mesh options.");
		}
	}

	/**
	 * Return the vert.x cluster port.
	 * 
	 * @return
	 */
	public Integer getVertxPort() {
		return vertxPort;
	}

	/**
	 * Set the vert.x cluster port.
	 * 
	 * @param vertxPort
	 */
	public void setVertxPort(Integer vertxPort) {
		this.vertxPort = vertxPort;
	}

}
