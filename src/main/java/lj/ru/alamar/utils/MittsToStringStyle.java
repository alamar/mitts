package lj.ru.alamar.utils;

import java.util.Collection;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MittsToStringStyle extends ToStringStyle {
    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor.</p>
     */
    public MittsToStringStyle() {
        super();

        this.setUseIdentityHashCode(false);
        this.setUseShortClassName(true);
    }

    @Override
    public void appendDetail(final StringBuffer buffer, final String fieldName, final Object value) {
        if (!ClassUtils.isPrimitiveWrapper(value.getClass()) &&
            !String.class.equals(value.getClass()) &&
            accept(value.getClass())) {
            buffer.append(ReflectionToStringBuilder.toString(value, this));
        } else {
            super.appendDetail(buffer, fieldName, value);
        }
    }

    @Override
    protected void appendDetail(final StringBuffer buffer, final String fieldName, final Collection<?> coll) {
        appendClassName(buffer, coll);
        appendDetail(buffer, fieldName, coll.toArray());
    }

    /**
     * Returns whether or not to recursively format the given <code>Class</code>.
     * By default, this method always returns {@code true}, but may be overwritten by
     * sub-classes to filter specific classes.
     *
     * @param clazz
     *            The class to test.
     * @return Whether or not to recursively format the given <code>Class</code>.
     */
    protected boolean accept(final Class<?> clazz) {
        return true;
    }
}
