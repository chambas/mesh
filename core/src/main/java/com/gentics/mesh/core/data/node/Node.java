package com.gentics.mesh.core.data.node;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Stack;

import com.gentics.mesh.core.Page;
import com.gentics.mesh.core.data.Language;
import com.gentics.mesh.core.data.MeshAuthUser;
import com.gentics.mesh.core.data.MeshCoreVertex;
import com.gentics.mesh.core.data.NodeGraphFieldContainer;
import com.gentics.mesh.core.data.Project;
import com.gentics.mesh.core.data.SchemaContainer;
import com.gentics.mesh.core.data.Tag;
import com.gentics.mesh.core.data.User;
import com.gentics.mesh.core.rest.node.NodeBreadcrumbResponse;
import com.gentics.mesh.core.rest.node.NodeResponse;
import com.gentics.mesh.core.rest.schema.Schema;
import com.gentics.mesh.core.rest.user.NodeReferenceImpl;
import com.gentics.mesh.handler.InternalActionContext;
import com.gentics.mesh.path.Path;
import com.gentics.mesh.path.PathSegment;
import com.gentics.mesh.query.impl.PagingParameter;
import com.gentics.mesh.util.InvalidArgumentException;

import rx.Observable;

public interface Node extends MeshCoreVertex<NodeResponse, Node> {

	public static final String TYPE = "node";

	/**
	 * Add the given tag to the list of tags for this node.
	 * 
	 * @param tag
	 */
	void addTag(Tag tag);

	/**
	 * Remove the given tag from the list of tags for this node.
	 * 
	 * @param tag
	 */
	void removeTag(Tag tag);

	/**
	 * Return a list of tags that were assigned to this node.
	 * 
	 * @return
	 */
	List<? extends Tag> getTags();

	/**
	 * Return the schema container that holds the schema that is used in combination with this node.
	 * 
	 * @return
	 */
	SchemaContainer getSchemaContainer();

	/**
	 * Set the schema container that is used in combination with this node.
	 * 
	 * @param schema
	 */
	void setSchemaContainer(SchemaContainer schema);

	/**
	 * Shortcut method for getSchemaContainer().getSchema()
	 * 
	 * @return
	 */
	Schema getSchema();

	/**
	 * Return the field container for the given language.
	 * 
	 * @param language
	 * @return
	 */
	NodeGraphFieldContainer getGraphFieldContainer(Language language);

	/**
	 * Return the field container for the given language. Create the container when non was found.
	 * 
	 * @param language
	 * @return
	 */
	NodeGraphFieldContainer getOrCreateGraphFieldContainer(Language language);

	/**
	 * Return a list of graph field containers for the node.
	 * 
	 * @return
	 */
	List<? extends NodeGraphFieldContainer> getGraphFieldContainers();

	/**
	 * Return a page of tags that are assigned to the node.
	 * 
	 * @param ac
	 * @return
	 * @throws InvalidArgumentException
	 */
	Page<? extends Tag> getTags(InternalActionContext ac) throws InvalidArgumentException;

	/***
	 * Create link between the nodes.
	 * 
	 * @param node
	 */
	void createLink(Node node);

	/**
	 * Return a list of language names.
	 * 
	 * @return
	 */
	List<String> getAvailableLanguageNames();

	/**
	 * Return the project of the node.
	 * 
	 * @return
	 */
	Project getProject();

	/**
	 * Set the project of the node.
	 * 
	 * @param project
	 */
	void setProject(Project project);

	/**
	 * Return the list of children for this node.
	 * 
	 * @return
	 */
	List<? extends Node> getChildren();

	/**
	 * Returns the parent node of this node.
	 * 
	 * @return
	 */
	Node getParentNode();

	/**
	 * Set the parent node of this node
	 * 
	 * @param parentNode
	 */
	void setParentNode(Node parentNode);

	/**
	 * Create a child node in this node.
	 * 
	 * @param creator
	 * @param schemaContainer
	 * @param project
	 * @return
	 */
	Node create(User creator, SchemaContainer schemaContainer, Project project);

	/**
	 * Return a page with child nodes that are visible to the given user.
	 * 
	 * @param requestUser
	 * @param languageTags
	 * @param pagingParameter
	 * @return
	 * @throws InvalidArgumentException
	 */
	Page<? extends Node> getChildren(MeshAuthUser requestUser, List<String> languageTags, PagingParameter pagingParameter)
			throws InvalidArgumentException;

	/**
	 * Returns the i18n display name for the node. The display name will be determined by loading the i18n field value for the display field parameter of the
	 * node's schema. It may be possible that no display name can be returned since new nodes may not have any values.
	 * 
	 * @param ac
	 * @return
	 */
	String getDisplayName(InternalActionContext ac);

	/**
	 * Find a node field container that matches the nearest possible value for the ?lang= request parameter. When a user requests a node using ?lang=de,en and
	 * there is no de version the en version will be selected and returned.
	 * 
	 * @param ac
	 * @return Next matching field container or null when no language matched
	 */
	NodeGraphFieldContainer findNextMatchingFieldContainer(InternalActionContext ac);

	/**
	 * Set the published flag.
	 * 
	 * @param published
	 */
	void setPublished(boolean published);

	/**
	 * Return the published flag state.
	 * 
	 * @return
	 */
	boolean isPublished();

	/**
	 * Move this node into the target node.
	 * 
	 * @param ac
	 * @param targetNode
	 * @return
	 */
	Observable<Void> moveTo(InternalActionContext ac, Node targetNode);

	/**
	 * Transform the node into a node reference rest model.
	 * 
	 * @param ac
	 */
	Observable<NodeReferenceImpl> transformToReference(InternalActionContext ac);

	/**
	 * Transform information from the node into a breadcrumb rest model.
	 * 
	 * @param ac
	 * @return
	 */
	Observable<NodeBreadcrumbResponse> transformToBreadcrumb(InternalActionContext ac);

	/**
	 * Delete the language container for the given language.
	 * 
	 * @param ac
	 * @param language
	 * @return
	 */
	Observable<? extends Node> deleteLanguageContainer(InternalActionContext ac, Language language);

	/**
	 * Return the path segment of this node.
	 * 
	 * @return
	 */
	Observable<String> getPathSegment(InternalActionContext ac);

	/**
	 * Return the full path to this node.
	 * 
	 * @param ac
	 * @return
	 */
	Observable<String> getPath(InternalActionContext ac);

	/**
	 * Resolve the given path and return the path object that contains the resolved nodes.
	 * 
	 * @param nodePath
	 * @param pathStack
	 * @return
	 */
	Observable<Path> resolvePath(Path nodePath, Stack<String> pathStack);

	/**
	 * Check whether the node provides the given segment for any language or binary attribute filename return the segment information.
	 * 
	 * @param segment
	 * @return Segment information or null if this node is not providing the given segment
	 */
	PathSegment getSegment(String segment);

	/**
	 * Return the webroot path to the node in the given language.
	 * 
	 * @param language
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	Observable<String> getPath(Language language) throws UnsupportedEncodingException;

	/**
	 * Return the path segment value of this node in the given language.
	 * 
	 * @param language
	 * @return
	 */
	Observable<String> getPathSegment(Language language);

}
